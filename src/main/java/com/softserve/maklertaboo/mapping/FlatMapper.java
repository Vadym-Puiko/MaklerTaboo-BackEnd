package com.softserve.maklertaboo.mapping;

import com.softserve.maklertaboo.dto.flat.FlatDto;
import com.softserve.maklertaboo.entity.Flat;
import org.springframework.stereotype.Component;

@Component
public class FlatMapper implements MapperToDto<Flat, FlatDto>, MapperToEntity<FlatDto,Flat> {

    public FlatDto convertToDto(Flat flat) {

        FlatDto flatDto = new FlatDto();
        flatDto.setCreationDate(flat.getCreationDate());
        flatDto.setDescription(flat.getDescription());
        flatDto.setId(flat.getId());
        flatDto.setMonthPrice(flat.getMonthPrice());
        flatDto.setTitle(flat.getTitle());
        flatDto.setPhotoUrl(flat.getFlatPhotoList().get(0).getUrl());
        flatDto.setAddress(flat.getAddress());
        return flatDto;
    }

    public Flat convertToEntity(FlatDto flatDto) {
        Flat flat = new Flat();
        flat.setCreationDate(flatDto.getCreationDate());
        flat.setDescription(flatDto.getDescription());
        flat.setId(flatDto.getId());
        flat.setMonthPrice(flatDto.getMonthPrice());
        flat.setTitle(flatDto.getTitle());
        flat.setAddress(flatDto.getAddress());
        return flat;
    }

}
