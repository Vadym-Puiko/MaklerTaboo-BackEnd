package com.softserve.maklertaboo.mapping.flat;

import com.softserve.maklertaboo.dto.flat.FlatDto;
import com.softserve.maklertaboo.entity.Tag;
import com.softserve.maklertaboo.entity.flat.FavoriteFlat;
import com.softserve.maklertaboo.mapping.MapperToDto;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

/**
 * Class that used by {@link MapperToDto} for mapping {@link FavoriteFlat}
 * into {@link FlatDto}.
 *
 * @author Roman Blavatskyi
 */
@Component
public class FavoriteFlatMapper implements MapperToDto<FavoriteFlat, FlatDto> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM HH:mm");

    /**
     * Method for converting {@link FavoriteFlat} into {@link FlatDto}.
     *
     * @param entity {@link FavoriteFlat}
     * @return {@link FlatDto}
     */
    @Override
    public FlatDto convertToDto(FavoriteFlat entity) {
        FlatDto flatDto = new FlatDto();
        flatDto.setId(entity.getFlat().getId());
        flatDto.setMonthPrice(entity.getFlat().getMonthPrice());
        flatDto.setDescription(entity.getFlat().getDescription());
        flatDto.setTitle(entity.getFlat().getTitle());
        if (entity.getFlat().getFlatPhotoList().size() > 0) {
            flatDto.setPhotoUrl(entity.getFlat().getFlatPhotoList().get(0).getUrl());
        }
        flatDto.setCreationDate(dateFormat.format(entity.getFlat().getCreationDate()));
        flatDto.setAddress(entity.getFlat().getAddress());
        flatDto.setTags(entity.getFlat().getTags()
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList())
        );
        return flatDto;
    }
}
