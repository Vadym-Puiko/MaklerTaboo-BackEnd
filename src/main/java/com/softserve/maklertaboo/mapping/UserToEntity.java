package com.softserve.maklertaboo.mapping;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.User;

public class UserToEntity implements MapperToEntity<UserDto, User> {

    @Override
    public User convertToEntity(UserDto userDto) {

        return null;
    }
}
