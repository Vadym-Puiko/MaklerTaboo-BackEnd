package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.constant.ErrorMessage;
import com.softserve.maklertaboo.dto.flat.FlatSearchParametersDto;
import com.softserve.maklertaboo.dto.flat.NewFlatDto;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.flat.FlatLocation;
import com.softserve.maklertaboo.entity.flat.FlatSearchParameters;
import com.softserve.maklertaboo.entity.photo.FlatPhoto;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.exception.exceptions.FlatNotFoundException;
import com.softserve.maklertaboo.exception.exceptions.UserNotFoundException;
import com.softserve.maklertaboo.mapping.flat.FlatMapper;
import com.softserve.maklertaboo.mapping.flat.FlatSearchMapper;
import com.softserve.maklertaboo.mapping.flat.NewFlatMapper;
import com.softserve.maklertaboo.model.BASE64DecodedMultipartFile;
import com.softserve.maklertaboo.repository.FlatRepository;
import com.softserve.maklertaboo.repository.search.FlatFullTextSearch;
import com.softserve.maklertaboo.repository.search.FlatSearchRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import com.softserve.maklertaboo.service.map.FlatLocationService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.softserve.maklertaboo.constant.ErrorMessage.FLAT_NOT_FOUND_BY_ID;

@Data
@Service
public class FlatService {
    private final FlatRepository flatRepository;
    private final FlatSearchRepository flatSearchRepository;
    private final NewFlatMapper newFlatMapper;
    private final FlatFullTextSearch flatFullTextSearch;
    private final FlatSearchMapper flatSearchMapper;
    private final TagService tagService;
    private final UserRepository userRepository;
    private final FlatMapper flatMapper;
    private final AmazonStorageService amazonStorageService;
    private final RequestForVerificationService requestForVerificationService;
    private final FlatLocationService flatLocationService;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public FlatService(FlatRepository flatRepository,
                       FlatSearchRepository flatSearchRepository,
                       NewFlatMapper newFlatMapper,
                       FlatFullTextSearch flatFullTextSearch,
                       FlatSearchMapper flatSearchMapper,
                       TagService tagService,
                       UserRepository userRepository,
                       FlatMapper flatMapper,
                       AmazonStorageService amazonStorageService,
                       FlatLocationService flatLocationService,
                       @Lazy RequestForVerificationService requestForVerificationService,
                       JWTTokenProvider jwtTokenProvider) {
        this.flatRepository = flatRepository;
        this.flatSearchRepository = flatSearchRepository;
        this.newFlatMapper = newFlatMapper;
        this.flatFullTextSearch = flatFullTextSearch;
        this.flatSearchMapper = flatSearchMapper;
        this.tagService = tagService;
        this.userRepository = userRepository;
        this.flatMapper = flatMapper;
        this.amazonStorageService = amazonStorageService;
        this.requestForVerificationService = requestForVerificationService;
        this.flatLocationService = flatLocationService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Cacheable("flats")
    public Page<Flat> getAll(Pageable pageable) {
        Page<Flat> flats = flatRepository.findAllByIsActiveIsTrue(pageable);
        return flats;
    }

    @Cacheable("flats")
    public Page<Flat> getByParameters(FlatSearchParametersDto flatParametersDto, Pageable pageable) {
        FlatSearchParameters flatParameters = flatSearchMapper.convertToEntity(flatParametersDto);
        if (flatParameters.getPriceHigh() != null) {
            return flatFullTextSearch.search(flatParameters, pageable);
        } else {
            return getAll(pageable);
        }
    }

    @Cacheable("flats")
    public Flat getById(Long id) {
        Flat flat = flatRepository.findById(id).orElse(null);
        if (flat == null) {
            throw new FlatNotFoundException(FLAT_NOT_FOUND_BY_ID + id);
        }
        return flat;
    }

    private void savePhotos(NewFlatDto newFlatDto, Flat flat) {
        List<FlatPhoto> photos = new ArrayList<>();

        for (String base64 : newFlatDto.getBase64Photos()) {

            FlatPhoto flatPhoto = new FlatPhoto();
            flatPhoto.setFlat(flat);
            flatPhoto.setUrl(
                    amazonStorageService
                            .uploadFile(BASE64DecodedMultipartFile
                                    .base64ToMultipart(base64)));
            photos.add(flatPhoto);
        }
        flat.setFlatPhotoList(photos);
    }

    @CachePut("flats")
    public void saveFlat(NewFlatDto newFlatDto) {
        newFlatDto.setEmail(jwtTokenProvider.getCurrentUser().getEmail());
        Flat flat = newFlatMapper.convertToEntity(newFlatDto);
        savePhotos(newFlatDto, flat);
        flat.setTags(tagService.getTags(newFlatDto.getTags()));
        flat.setOwner(userRepository.findUserByEmail(newFlatDto.getEmail()).orElseThrow(
                () -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND)));
        flat.setCreationDate(new Date());
        FlatLocation flatLocation = flatLocationService.generateLocation(flat.getAddress());
        flatLocation.setFlat(flat);
        flat.setFlatLocation(flatLocation);
        flatRepository.save(flat);

        requestForVerificationService.createFlatRequest(flat);
        requestForVerificationService.createForBan(flat);
    }

    @CachePut("flats")
    public void activate(Long id) {
        Flat flat = flatRepository.findById(id).orElseThrow(
                () -> new FlatNotFoundException(FLAT_NOT_FOUND_BY_ID + id));
        if (flat == null) {
            throw new FlatNotFoundException(FLAT_NOT_FOUND_BY_ID + id);
        }
        flat.setIsActive(true);
        flatRepository.save(flat);
    }

    @CachePut("flats")
    public void deactivateFlat(Long id) {
        Flat flat = flatRepository.findById(id).orElseThrow(
                () -> new FlatNotFoundException(FLAT_NOT_FOUND_BY_ID + id));
        if (flat == null) {
            throw new FlatNotFoundException(FLAT_NOT_FOUND_BY_ID + id);
        }
        flat.setIsActive(false);
        flatRepository.save(flat);
    }

    public List<Flat> findByOwnerId(Long id) {
        User user = userRepository.findById(id).get();
        return flatRepository.findByOwnerAndIsActiveIsTrue(user);
    }

    public Long countAllByIsActive(boolean b) {
        return flatRepository.countAllByIsActive(b);
    }

    public long countAllByOwner(User owner) {
        return flatRepository.countAllByOwner(owner);
    }

    /**
     * Method for deleting publication of {@link Flat}.
     *
     * @param id a value of {@link Long}
     * @author Vadym Puiko
     */
    public void deleteFlat(Long id) {
        Flat flat = flatRepository.findById(id).orElseThrow(
                () -> new FlatNotFoundException(FLAT_NOT_FOUND_BY_ID + id));
        flatRepository.delete(flat);
    }

    public void saveFlat(Flat flat) {
        flatRepository.save(flat);
    }
}
