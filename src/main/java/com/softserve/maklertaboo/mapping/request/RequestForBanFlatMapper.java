package com.softserve.maklertaboo.mapping.request;

import com.softserve.maklertaboo.dto.request.RequestForBanFlatDto;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.request.RequestForBanFlat;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.mapping.flat.FlatMapper;
import com.softserve.maklertaboo.repository.FlatRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Class that used by {@link MapperToDto}, {@link MapperToEntity} fot mapping {@link RequestForBanFlatDto}
 * into {@link RequestForBanFlat} and versa.
 */
@Component
@AllArgsConstructor
public class RequestForBanFlatMapper implements MapperToDto<RequestForBanFlat, RequestForBanFlatDto>,
        MapperToEntity<RequestForBanFlatDto, RequestForBanFlat> {

    private UserRepository userRepository;
    private FlatRepository flatRepository;
    private UserMapper userMapper;
    private FlatMapper flatMapper;

    /**
     * Method for converting {@link RequestForBanFlat} into {@link RequestForBanFlatDto}.
     *
     * @param entity object to convert.
     * @return converted object.
     */
    @Override
    public RequestForBanFlatDto convertToDto(RequestForBanFlat entity) {
        RequestForBanFlatDto requestForBanFlatDto = new RequestForBanFlatDto();
        requestForBanFlatDto.setStatus(entity.getStatusForVerification());
        requestForBanFlatDto.setCreationDate(entity.getCreationDate());
        requestForBanFlatDto.setId(entity.getId());
        requestForBanFlatDto.setFlat(flatMapper.convertToDto(entity.getFlat()));
        requestForBanFlatDto.setAuthor(userMapper.convertToDto(entity.getAuthor()));
        return requestForBanFlatDto;
    }

    /**
     * Method for converting {@link RequestForBanFlatDto} into {@link RequestForBanFlat}.
     *
     * @param dto object to convert.
     * @return converted object.
     */
    @Override
    public RequestForBanFlat convertToEntity(RequestForBanFlatDto dto) {
        RequestForBanFlat requestForBanFlat = new RequestForBanFlat();
        requestForBanFlat.setStatusForVerification(dto.getStatus());
        User user = userRepository.findById(dto.getAuthor().getId()).orElseThrow(IllegalAccessError::new);
        requestForBanFlat.setAuthor(user);
        Flat flat = flatRepository.findById(dto.getFlat().getId()).orElseThrow(IllegalAccessError::new);
        requestForBanFlat.setFlat(flat);
        return requestForBanFlat;
    }
}
