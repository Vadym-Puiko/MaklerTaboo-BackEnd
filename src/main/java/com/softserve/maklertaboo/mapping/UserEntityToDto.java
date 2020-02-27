package com.softserve.maklertaboo.mapping;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.User;

public class UserEntityToDto implements MapperToDto<User, UserDto> {

    private UserDto userDto = new UserDto();

    @Override
    public UserDto convertToDto(User user) {
        if (user == null) {
            user = new User();
        }
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPhotoUrl(userDto.getPhotoUrl());
        return userDto;
    }
}
