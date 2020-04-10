package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.Passport;
import com.softserve.maklertaboo.entity.TelegramUserData;
import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.enums.UserStatus;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.repository.user.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;


@RunWith(PowerMockRunner.class)
@PrepareForTest(UserService.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AmazonStorageService amazonStorageService;

    @InjectMocks
    private UserService userService;


    @Test
    void getAuthentication() {
    }

    @Test
    void validateLogin() {
    }

    @Test
    void comparePasswordLogin() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void updateUserIntoAdminPanel() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void updatePhoto() {
    }

    @Test
    void updateRole() {
    }

    @Test
    void deletePhoto() {
    }

    @Test
    void searchUserByUsername() {
    }

    @Test
    void searchUserByEmail() {
    }

    @Test
    void searchUserByPhone() {
    }

    @Test
    void findByPage() {
        int pageNumber = 0;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        User user = new User();
        user.setUsername("Roman");

        UserDto userDto = new UserDto();
        userDto.setUsername("Roman");

        Page<User> usersPage = new PageImpl<>(Collections.singletonList(user), pageable, 1);
        List<UserDto> userDtos = Collections.singletonList(userDto);

        PageableDto<UserDto> userPageableDto =
                new PageableDto<>(userDtos,
                        userDtos.size(), 0);

        ReflectionTestUtils.setField(userService, "modelMapper", new ModelMapper());

        when(userRepository.findAll(pageable)).thenReturn(usersPage);

        assertEquals(userPageableDto, userService.findByPage(pageable));
        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    void findAllUser() {
    }

    @Test
    void findUserById() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findByUsername() {
    }

    @Test
    void emailExists() {
    }

    @Test
    void updateAccessTokens() {
    }

    @Test
    void changeUserPassword() {
    }

    @Test
    void getCurrentUserDto() {
    }
}