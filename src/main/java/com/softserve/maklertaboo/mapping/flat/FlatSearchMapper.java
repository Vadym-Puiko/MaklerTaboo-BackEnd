package com.softserve.maklertaboo.mapping.flat;

import com.softserve.maklertaboo.dto.flat.FlatSearchParametersDto;
import com.softserve.maklertaboo.entity.flat.FlatSearchParameters;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class FlatSearchMapper implements MapperToEntity<FlatSearchParametersDto, FlatSearchParameters> {

    private TagRepository tagRepository;

    @Autowired
    public FlatSearchMapper(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public FlatSearchParameters convertToEntity(FlatSearchParametersDto dto) {
        FlatSearchParameters flatSearchParameters = new FlatSearchParameters();
        flatSearchParameters.setFloorHigh(dto.getFloorHigh());
        flatSearchParameters.setFloorLow(dto.getFloorLow());
        flatSearchParameters.setMaxNumberOfRooms(dto.getMaxNumberOfRooms());
        flatSearchParameters.setMinNumberOfRooms(dto.getMinNumberOfRooms());
        flatSearchParameters.setPriceHigh(dto.getPriceHigh());
        flatSearchParameters.setPriceLow(dto.getPriceLow());
        flatSearchParameters.setRegions(dto.getRegions());
        Set<String> searchText = new HashSet<>();

        if(dto.getTags()!=null) {
            for (String tag : dto.getTags()) {
                if (tagRepository.findByName(tag).isEmpty()) {
                    searchText.add(tag);
                    dto.getTags().remove(tag);
                }
            }
        }
        flatSearchParameters.setSearchText(searchText);
        flatSearchParameters.setTags(dto.getTags());
        return flatSearchParameters;
    }
}
