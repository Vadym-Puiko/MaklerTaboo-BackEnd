package com.softserve.maklertaboo.mapping.flat;

import com.softserve.maklertaboo.dto.flat.FlatLocationDto;
import com.softserve.maklertaboo.entity.flat.FlatLocation;
import com.softserve.maklertaboo.mapping.MapperToDto;
import org.springframework.stereotype.Service;

@Service
public class FlatLocationMapper implements MapperToDto<FlatLocation, FlatLocationDto> {
    @Override
    public FlatLocationDto convertToDto(FlatLocation flatLocation) {
        FlatLocationDto flatLocationDto = new FlatLocationDto();
        flatLocationDto.setFlatId(flatLocation.getFlat().getId());
        flatLocationDto.setLatitude(flatLocation.getLatitude());
        flatLocationDto.setLongitude(flatLocation.getLongitude());
        return flatLocationDto;
    }
}
