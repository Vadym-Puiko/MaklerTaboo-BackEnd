package com.softserve.maklertaboo.mapping.flat;

import com.softserve.maklertaboo.dto.flat.FlatDto;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.Tag;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@Component
public class FlatMapper implements MapperToDto<Flat, FlatDto>, MapperToEntity<FlatDto, Flat> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM HH:mm");

    public FlatDto convertToDto(Flat flat) {
        FlatDto flatDto = new FlatDto();

        if(flat.getCreationDate()!=null) {
            flatDto.setCreationDate(dateFormat.format(flat.getCreationDate()));
        }
        flatDto.setDescription(flat.getDescription());
        flatDto.setId(flat.getId());

        flatDto.setMonthPrice(flat.getMonthPrice());
        flatDto.setTitle(flat.getTitle());

        flatDto.setPhotoUrl(flat.getFlatPhotoList().get(0).getUrl());
        flatDto.setAddress(flat.getAddress());

        flatDto.setTags(
                flat.getTags()
                        .stream()
                        .map(Tag::getName)
                        .collect(Collectors.toList())
        );
        return flatDto;
    }

    @SneakyThrows
    public Flat convertToEntity(FlatDto flatDto) {
        Flat flat = new Flat();
        flat.setCreationDate(dateFormat.parse(flatDto.getCreationDate()));

        flat.setDescription(flatDto.getDescription());
        flat.setId(flatDto.getId());

        flat.setMonthPrice(flatDto.getMonthPrice());
        flat.setTitle(flatDto.getTitle());

        flat.setAddress(flatDto.getAddress());
        return flat;
    }

}
