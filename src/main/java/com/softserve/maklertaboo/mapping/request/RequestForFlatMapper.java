package com.softserve.maklertaboo.mapping.request;

import com.softserve.maklertaboo.dto.request.RequestForFlatDto;
import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.request.RequestForFlatVerification;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.mapping.flat.FlatMapper;
import com.softserve.maklertaboo.repository.FlatRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RequestForFlatMapper implements MapperToDto<RequestForFlatVerification, RequestForFlatDto>,
        MapperToEntity<RequestForFlatDto, RequestForFlatVerification> {

    private UserRepository userRepository;
    private FlatRepository flatRepository;
    private UserMapper userMapper;
    private FlatMapper flatMapper;

    @Autowired
    public RequestForFlatMapper(UserRepository userRepository, FlatRepository flatRepository,
                                UserMapper userMapper, FlatMapper flatMapper) {
        this.userRepository = userRepository;
        this.flatRepository = flatRepository;
        this.userMapper = userMapper;
        this.flatMapper = flatMapper;
    }


    @Override
    public RequestForFlatDto convertToDto(RequestForFlatVerification requestForFlat) {
        RequestForFlatDto requestForFlatDto = new RequestForFlatDto();

        requestForFlatDto.setCreationDate(LocalDateTime.now());

        requestForFlatDto.setVerificationDate(LocalDateTime.now());

        requestForFlatDto.setId(requestForFlat.getId());

        requestForFlatDto.setStatus(requestForFlat.getStatus());


        requestForFlatDto.setFlat(flatMapper.convertToDto(requestForFlat.getFlat()));

        requestForFlatDto.setAuthor(userMapper.convertToDto(requestForFlat.getAuthor()));

        return requestForFlatDto;
    }

    @Override
    public RequestForFlatVerification convertToEntity(RequestForFlatDto dto) {
        RequestForFlatVerification requestForFlatVerification = new RequestForFlatVerification();

        User user = userRepository.findById(dto.getAuthor().getId()).orElseThrow(IllegalAccessError::new);

        requestForFlatVerification.setAuthor(user);

        Flat flat = flatRepository.findById(dto.getFlat().getId()).orElseThrow(IllegalAccessError::new);

        requestForFlatVerification.setFlat(flat);

        return requestForFlatVerification;
    }
}
