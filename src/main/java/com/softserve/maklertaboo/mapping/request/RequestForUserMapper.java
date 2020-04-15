package com.softserve.maklertaboo.mapping.request;

import com.softserve.maklertaboo.dto.request.RequestForUserDto;
import com.softserve.maklertaboo.entity.request.RequestForUserVerification;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RequestForUserMapper implements MapperToDto<RequestForUserVerification, RequestForUserDto>,
        MapperToEntity<RequestForUserDto, RequestForUserVerification> {

    private UserMapper userMapper;
    private UserRepository userRepository;

    @Autowired
    public RequestForUserMapper(UserMapper flatMapper, UserRepository userRepository) {
        this.userMapper = flatMapper;
        this.userRepository = userRepository;
    }

    @Override
    public RequestForUserDto convertToDto(RequestForUserVerification requestForUser) {
        RequestForUserDto requestForUserDto = new RequestForUserDto();

        requestForUserDto.setCreationDate(LocalDateTime.now());

        requestForUserDto.setVerificationDate(LocalDateTime.now());

        requestForUserDto.setId(requestForUser.getId());

        requestForUserDto.setStatus(requestForUser.getStatus());

        requestForUserDto.setAuthor(userMapper.convertToDto(requestForUser.getAuthor()));

        return requestForUserDto;
    }

    @Override
    public RequestForUserVerification convertToEntity(RequestForUserDto dto) {

        RequestForUserVerification request = new RequestForUserVerification();

        request.setType(dto.getType());

        User user = userRepository.findById(dto.getAuthor().getId()).orElseThrow(IllegalAccessError::new);

        request.setAuthor(user);

        return request;
    }
}
