package com.softserve.maklertaboo.security.service;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class OwnSecurityService {

    private final UserMapper userMapper;

    public OwnSecurityService(UserMapper userMapper){
        this.userMapper = userMapper;
    }
    public void register(UserDto userDto) {
        User user = createNewRegisteredUser(userDto);

    }

    private User createNewRegisteredUser(UserDto userDto){
        return userMapper.convertToEntity(userDto);
    }
}
