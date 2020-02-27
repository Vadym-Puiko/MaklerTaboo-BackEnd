package com.softserve.maklertaboo.mapping;

import com.softserve.maklertaboo.dto.user.UserAccountDto;
import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements MapperToDto<User, UserAccountDto>, MapperToEntity<UserDto, User> {

    @Override
    public UserAccountDto convertToDto(User user) {
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setUsername(user.getUsername());
        userAccountDto.setEmail(user.getEmail());
        userAccountDto.setPhoneNumber(user.getPhoneNumber());
        userAccountDto.setPhotoUrl(user.getPhotoUrl());
        return userAccountDto;
    }

    @Override
    public User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPhotoUrl(userDto.getPhotoUrl());
        return user;
    }

}
