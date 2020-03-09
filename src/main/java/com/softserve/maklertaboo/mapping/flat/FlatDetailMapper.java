package com.softserve.maklertaboo.mapping.flat;

import com.softserve.maklertaboo.dto.flat.FlatDetailDto;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.Tag;
import com.softserve.maklertaboo.entity.photo.FlatPhoto;
import com.softserve.maklertaboo.mapping.MapperToDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FlatDetailMapper implements MapperToDto<Flat, FlatDetailDto> {
    @Override
    public FlatDetailDto convertToDto(Flat flat) {

        FlatDetailDto flatDetailDto = new FlatDetailDto();
        flatDetailDto.setTitle(flat.getTitle());

        flatDetailDto.setDescription(flat.getDescription());
        flatDetailDto.setMonthPrice(flat.getMonthPrice());

        if (flat.getOwner() != null) {
            flatDetailDto.setUsername(flat.getOwner().getUsername());
            flatDetailDto.setUserPhoto(flat.getOwner().getPhotoUrl());
        }
        if (flat.getOwner() != null) {
            flatDetailDto.setCreationDate(String.valueOf(flat.getCreationDate()));
        }
        flatDetailDto.setAddress(flat.getAddress());
        flatDetailDto.setRegion(flat.getDistrict());

        flatDetailDto.setPhotos(flat.getFlatPhotoList().stream().map(FlatPhoto::getUrl).collect(Collectors.toList()));
        flatDetailDto.setTags(flat.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));

        flatDetailDto.setNumberOfRooms(flat.getNumberOfRooms());
        flatDetailDto.setFloor(flat.getFloor());

        return flatDetailDto;
    }

}
