package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.flat.FlatDetailDto;
import com.softserve.maklertaboo.dto.flat.FlatSearchParametersDto;
import com.softserve.maklertaboo.dto.flat.NewFlatDto;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.flat.FlatSearchParameters;
import com.softserve.maklertaboo.mapping.flat.FlatMapper;
import com.softserve.maklertaboo.mapping.flat.FlatSearchMapper;
import com.softserve.maklertaboo.mapping.flat.NewFlatMapper;
import com.softserve.maklertaboo.repository.FlatRepository;
import com.softserve.maklertaboo.repository.search.FlatFullTextSearch;
import com.softserve.maklertaboo.repository.search.FlatSearchRepository;
import lombok.Data;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class FlatService {

    private final FlatRepository flatRepository;
    private final FlatSearchRepository flatSearchRepository;
    private final NewFlatMapper newFlatMapper;
    private final FlatFullTextSearch flatFullTextSearch;
    private final FlatSearchMapper flatSearchMapper;
    @Autowired
    FlatMapper flatMapper;

    @Autowired
    public FlatService(FlatRepository flatRepository,
                       FlatSearchRepository flatSearchRepository,
                       NewFlatMapper newFlatMapper,
                       FlatSearchMapper flatSearchMapper,
                       FlatFullTextSearch flatFullTextSearch) {
        this.flatSearchMapper = flatSearchMapper;
        this.newFlatMapper = newFlatMapper;
        this.flatRepository = flatRepository;
        this.flatSearchRepository = flatSearchRepository;
        this.flatFullTextSearch = flatFullTextSearch;
    }

    @Cacheable("flats")
    public Page<Flat> getAll(Pageable pageable) {
        Page<Flat> flats = flatRepository.findAllByIsActiveIsTrue(pageable);
        return flats;
    }

    @Cacheable("flats")
    public Page<Flat> getByParameters(FlatSearchParametersDto flatParametersDto, Pageable pageable) {
        FlatSearchParameters flatParameters = flatSearchMapper.convertToEntity(flatParametersDto);
        String searchText = StringUtils.join(flatParameters.getSearchText()).replaceAll(","," ");
        return flatSearchRepository.findByParams(flatParameters, pageable);
    }

    @Cacheable("flats")
    public Flat getById(Integer id) {
        return flatRepository.findById(Long.parseLong(id + "")).get();
    }

    @CacheEvict(cacheNames = "flats")
    public void saveFlat(NewFlatDto newFlatDto) {
        flatRepository.save(newFlatMapper.convertToEntity(newFlatDto));
    }

    @CacheEvict(cacheNames = "flats")
    public void activate(Long id) {
        Flat flat = flatRepository.findById(id).orElse(null);
        if (flat != null) {
            flat.setIsActive(true);
            flatRepository.save(flat);
        }
    }

    @CacheEvict(cacheNames = "flats")
    public void deactivateFlat(Long id) {
        Flat flat = flatRepository.findById(id).orElse(null);
        if (flat != null) {
            flat.setIsActive(false);
            flatRepository.save(flat);
        }
    }
}
