package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.flat.FlatSearchParametersDto;
import com.softserve.maklertaboo.dto.flat.NewFlatDto;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.flat.FlatSearchParameters;
import com.softserve.maklertaboo.entity.photo.FlatPhoto;
import com.softserve.maklertaboo.mapping.flat.FlatMapper;
import com.softserve.maklertaboo.mapping.flat.FlatSearchMapper;
import com.softserve.maklertaboo.mapping.flat.NewFlatMapper;
import com.softserve.maklertaboo.repository.FlatRepository;
import com.softserve.maklertaboo.repository.search.FlatFullTextSearch;
import com.softserve.maklertaboo.repository.search.FlatSearchRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.service.mailer.BASE64DecodedMultipartFile;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    ;

    @Autowired
    public FlatService(FlatRepository flatRepository,
                       FlatSearchRepository flatSearchRepository,
                       NewFlatMapper newFlatMapper,
                       FlatFullTextSearch flatFullTextSearch,
                       FlatSearchMapper flatSearchMapper,
                       TagService tagService,
                       UserRepository userRepository,
                       FlatMapper flatMapper,
                       AmazonStorageService amazonStorageService) {
        this.flatRepository = flatRepository;
        this.flatSearchRepository = flatSearchRepository;
        this.newFlatMapper = newFlatMapper;
        this.flatFullTextSearch = flatFullTextSearch;
        this.flatSearchMapper = flatSearchMapper;
        this.tagService = tagService;
        this.userRepository = userRepository;
        this.flatMapper = flatMapper;
        this.amazonStorageService = amazonStorageService;
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
    public Flat getById(Integer id) {
        return flatRepository.findById(Long.parseLong(id + "")).get();
    }

    @CachePut("flats")
    public void saveFlat(NewFlatDto newFlatDto) {
        Flat flat = newFlatMapper.convertToEntity(newFlatDto);
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
        flat.setTags(tagService.getTags(newFlatDto.getTags()));
        flat.setOwner(
                userRepository.findUserByEmail(newFlatDto.getEmail())
        );
        flat.setCreationDate(new Date());
        flatRepository.save(flat);
    }

    @CachePut("flats")
    public void activate(Long id) {
        Flat flat = flatRepository.findById(id).orElse(null);
        if (flat != null) {
            flat.setIsActive(true);
            flatRepository.save(flat);
        }
    }

    @CachePut("flats")
    public void deactivateFlat(Long id) {
        Flat flat = flatRepository.findById(id).orElse(null);
        if (flat != null) {
            flat.setIsActive(false);
            flatRepository.save(flat);
        }
    }

}
