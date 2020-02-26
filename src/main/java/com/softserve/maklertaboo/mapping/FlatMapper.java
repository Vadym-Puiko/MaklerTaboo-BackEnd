package com.softserve.maklertaboo.mapping;

import com.softserve.maklertaboo.dto.FlatDto;
import com.softserve.maklertaboo.entity.Flat;
import org.springframework.stereotype.Component;

@Component
public class FlatMapper {

    public FlatDto toFlatDto(Flat flat){

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

    public Flat toFlatDto(FlatDto flatDto){

        Flat flat = new Flat();
        flat.setCreationDate(flatDto.getCreationDate());
        flat.setDescription(flatDto.getDescription());
        flat.setId(flatDto.getId());
        flat.setMonthPrice(flatDto.getMonthPrice());
        flat.setTitle(flatDto.getTitle());
        flat.setPhotoUrl(flatDto.getFlatPhotoList().get(0).getUrl());
        flat.setAddress(flatDto.getAddress());

        return flat;
    }



}
