package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.dto.user.UserUpdateDto;
import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.enums.UserStatus;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.telegram.telegrambots.ApiContextInitializer;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@PrepareForTest(UserService.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private JWTTokenProvider jwtTokenProvider;

    @Mock
    private AmazonStorageService amazonStorageService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    @BeforeAll
    public static void initializeMock() {
        ApiContextInitializer.init();
    }

    private User user = new User(
            22L,
            "username",
            "email@gmail.com",
            "password",
            "+380988124589",
            "picture",
            UserRole.ROLE_USER,
            UserStatus.ACTIVATED
    );

    private User user2 = new User(
            2L,
            "username2",
            "email2@gmail.com",
            "password2",
            "+380988124000",
            "picture2",
            UserRole.ROLE_RENTER,
            UserStatus.ACTIVATED
    );

    private UserDto userDto = new UserDto(
            22L,
            "username",
            "email@gmail.com",
            "password",
            "+380988124589",
            "picture",
            UserRole.ROLE_USER.getStatus()
    );


    private UserUpdateDto userUpdateDto = new UserUpdateDto(
            4L,
            "usernameUpdateDto",
            "emailUpdateDto@gmail.com",
            "+380988124222",
            "pictureUpdateDto",
            UserRole.ROLE_RENTER.getStatus()
    );

    @Test
    public void saveUserSuccessfully() {
        final User user = new User(22L,
                "username",
                "email@gmail.com",
                "password",
                "+380988124589",
                "picture",
                UserRole.ROLE_USER,
                UserStatus.ACTIVATED);
        when(userRepository.existsUserByEmail(anyString()));
        userRepository.existsUserByUsername(anyString());
        userRepository.existsUserByPhoneNumber(anyString());

        User user1 = userMapper.convertToEntity(userDto);
        when(userRepository.getOne(anyLong())).thenReturn(user);
    }

    @Test
    public void saveFail() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void updateUserIntoAdminPanel() {
    }

    @Test
    void deleteUser() {
        final Long userId = 1L;

        userService.deleteUser(userId);
        userService.deleteUser(userId);

        verify(userRepository, times(2)).deleteById(userId);
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

}