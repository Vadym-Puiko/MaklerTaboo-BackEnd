package com.softserve.maklertaboo.mapping;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.UUID;

/**
 * Class that used by {@link MapperToDto}, {@link MapperToEntity} for mapping {@link UserDto} into {@link User}
 * and versa.
 */
@Component
@AllArgsConstructor
public class UserMapper implements MapperToDto<User, UserDto>, MapperToEntity<UserDto, User> {
    private final PasswordEncoder passwordEncoder;

    /**
     * Method for converting {@link User} into {@link UserDto}.
     *
     * @param user object to convert.
     * @return converted object.
     */
    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setPhotoUrl(user.getPhotoUrl());
        userDto.setUserRole(user.getRole().getStatus());
        return userDto;
    }

    /**
     * Method for converting {@link UserDto} into {@link User}.
     *
     * @param userDto object to convert.
     * @return converted object.
     */
    @Override
    public User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoneNumber(userDto.getPhoneNumber());
        if (userDto.getUserRole() != null) {
            user.setRole(UserRole.valueOf(userDto.getUserRole()));
        }
        user.setPhotoUrl(userDto.getPhotoUrl());

        user.setRefreshKey(UUID.randomUUID().toString());
        return user;
    }

}
