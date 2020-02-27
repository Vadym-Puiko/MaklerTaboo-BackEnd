package com.softserve.maklertaboo.mapping;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements MapperToDto<User, UserDto>, MapperToEntity<UserDto, User> {

    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setPhotoUrl(user.getPhotoUrl());
        return userDto;
    }

    @Override
    public User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPhotoUrl(userDto.getPhotoUrl());
        return user;
    }

}
