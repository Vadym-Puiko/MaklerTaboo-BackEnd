package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.constant.ErrorMessage;
import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.dto.user.UserUpdateDto;
import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.exception.exceptions.*;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.security.dto.JWTSuccessLogIn;
import com.softserve.maklertaboo.security.dto.JwtTokensDto;
import com.softserve.maklertaboo.security.dto.LoginDto;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.softserve.maklertaboo.constant.ErrorMessage.DELETE_USER_ERROR;
import static com.softserve.maklertaboo.constant.ErrorMessage.REFRESH_TOKEN_NOT_VALID;


@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JWTTokenProvider jwtTokenProvider;
    private final AmazonStorageService amazonStorageService;

    /**
     * Constructor with parameters
     *
     * @author Vadym Puiko
     */
    @Autowired
    public UserService(UserMapper userMapper,
                       UserRepository userRepository,
                       JWTTokenProvider jwtTokenProvider,
                       AmazonStorageService amazonStorageService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.amazonStorageService = amazonStorageService;
    }

    /**
     * Method that allow you to save new {@link User}.
     *
     * @param userDto a value of {@link UserDto}
     * @author Vadym Puiko
     */
    public void saveUser(UserDto userDto) {
        boolean existsUserByEmail = userRepository.existsUserByEmail(userDto.getEmail());
        boolean existsUserByUsername = userRepository.existsUserByUsername(userDto.getUsername());
        boolean existsUserByPhone = userRepository.existsUserByPhoneNumber(userDto.getPhoneNumber());
        if (existsUserByEmail || existsUserByUsername || existsUserByPhone) {
            throw new UserAlreadyExistsException(ErrorMessage.USER_ALREADY_EXISTS + userDto.getEmail());
        } else {
            User user = userMapper.convertToEntity(userDto);
            userRepository.save(user);
        }
    }

    /**
     * Method that allow you to update data of {@link User}.
     *
     * @param userUpdateDto a value of {@link UserUpdateDto}
     * @param email a value of {@link String}
     * @author Vadym Puiko
     */
    public void updateUser(String email, UserUpdateDto userUpdateDto) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UserNotUpdatedException(ErrorMessage.UPDATE_USER_ERROR + email));
        user.setUsername(userUpdateDto.getUsername());
        user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        user.setPhotoUrl(userUpdateDto.getPhotoUrl());
        userRepository.save(user);
    }

    /**
     * Method that allow you to update data of {@link User}.
     *
     * @param userUpdateDto a value of {@link UserUpdateDto}
     * @author Vadym Puiko
     */
    public void updateUserIntoAdminPanel(UserUpdateDto userUpdateDto) {
        User user = userRepository.findUserByEmail(userUpdateDto.getEmail()).orElseThrow(
                () -> new UserNotUpdatedException(ErrorMessage.UPDATE_USER_ERROR + userUpdateDto.getEmail()));
        user.setUsername(userUpdateDto.getUsername());
        user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        userRepository.save(user);
    }

    /**
     * Method for deleting account of {@link User}.
     *
     * @param id a value of {@link Long}
     * @author Vadym Puiko
     */
    public void deleteUser(Long id) {
        if (id == null) {
            throw new UserNotDeletedException(DELETE_USER_ERROR + id);
        } else {
            userRepository.deleteById(id);
        }
    }

    /**
     * Method that allow you to change profile picture
     *
     * @param multipartFile photo for saving.
     * @param email of current user
     * @author Vadym Puiko
     */
    public void updatePhoto(MultipartFile multipartFile, String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND + email));
        if (user.getPhotoUrl() != null) {
            amazonStorageService.deleteFile(user.getPhotoUrl());
        }
        String photoName = amazonStorageService.uploadFile(multipartFile);
        user.setPhotoUrl(photoName);
        userRepository.save(user);
    }

    /**
     * Method that allow you to update role of {@link User}.
     *
     * @param userId a value of {@link Long}
     * @param role a value of {@link UserRole}
     * @author Vadym Puiko
     */
    public void updateRole(Long userId, UserRole role) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + userId));
        user.setRole(role);
        userRepository.save(user);
    }

    /**
     * Method that allow you to delete profile picture
     *
     * @param email of current user
     * @author Vadym Puiko
     */
    public void deletePhoto(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND + email));
        amazonStorageService.deleteFile(user.getPhotoUrl());
        user.setPhotoUrl(null);
        userRepository.save(user);
    }

    /**
     * Method returns page of users by username without ROLE_ADMIN.
     *
     * @param username contains objects whose values determine the search parameters of the returned list.
     * @param pageable a value with pageable configuration.
     * @return a dto of {@link Page}.
     * @author Vadym Puiko.
     */
    public Page<UserDto> searchUserByUsername(Pageable pageable, String username) {
        return userRepository.findAllByUsernameLike(pageable, "%" + username + "%").map(userMapper::convertToDto);
    }

    /**
     * Method returns page of users by email without ROLE_ADMIN.
     *
     * @param email contains objects whose values determine the search parameters of the returned list.
     * @param pageable a value with pageable configuration.
     * @return a dto of {@link Page}.
     * @author Vadym Puiko.
     */
    public Page<UserDto> searchUserByEmail(Pageable pageable, String email) {
        return userRepository.findAllByEmailLike(pageable, "%" + email + "%").map(userMapper::convertToDto);
    }

    /**
     * Method returns page of users by phone without ROLE_ADMIN.
     *
     * @param phone contains objects whose values determine the search parameters of the returned list.
     * @param pageable a value with pageable configuration.
     * @return a dto of {@link Page}.
     * @author Vadym Puiko.
     */
    public Page<UserDto> searchUserByPhone(Pageable pageable, String phone) {
        return userRepository.findAllByPhoneNumberLike(pageable, "%" + phone + "%").map(userMapper::convertToDto);
    }

    /**
     * Method returns users by page without ROLE_ADMIN.
     *
     * @param pageable a value with pageable configuration.
     * @return a dto of {@link Page}.
     * @author Vadym Puiko
     */
    public Page<UserDto> findByPage(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::convertToDto);
    }

    /**
     * Find all {@link User}-s.
     *
     * @return a dto of {@link List}.
     * @author Vadym Puiko
     */
    public List<UserDto> findAllUser() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() != (UserRole.ROLE_ADMIN))
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Method that allow you to get {@link User} by ID.
     *
     * @param id a value of {@link Long}
     * @return {@link UserDto}
     * @author Vadym Puiko
     */
    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + id));
        return userMapper.convertToDto(user);
    }


    /**
     * Method that allow you to get {@link User} by ID.
     *
     * @param id a value of {@link Long}
     * @return {@link User}
     * @author Vadym Puiko
     */
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + id));
    }


    /**
     * Method that allow you to get {@link User} by email.
     *
     * @param email a value of {@link String}
     * @return {@link UserDto}
     * @author Vadym Puiko
     */
    public UserDto findByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND + email));
        return userMapper.convertToDto(user);
    }

    /**
     * Method that allow you to get {@link User} by username.
     *
     * @param username a value of {@link String}
     * @return {@link UserDto}
     * @author Vadym Puiko
     */
    public UserDto findByUsername(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND_BY_USERNAME + username));
        return userMapper.convertToDto(user);
    }

    /**
     * Method that check exists email in database for a given user.
     *
     * @param email - of current user
     * @return boolean check result
     * @author Vadym Puiko
     */
    public boolean emailExists(String email) {
        return userRepository.existsUserByEmail(email);
    }

    /**
     * Method that validation of login for a given user.
     *
     * @param loginDto - of current user
     * @return {@link JWTSuccessLogIn}
     * @author Mike Ostapiuk
     */
    public JWTSuccessLogIn validateLogin(LoginDto loginDto) {
        User user = userRepository.findUserByEmail(loginDto.getEmail()).orElseThrow(
                () -> new BadEmailOrPasswordException(ErrorMessage.BAD_EMAIL_OR_PASSWORD));
        return new JWTSuccessLogIn(user.getId(), user.getUsername(), user.getEmail(), user.getRole().name());
    }

    /**
     * Method that compare and check exist password, and login for a given user.
     *
     * @param loginDto - of current user
     * @param passwordEncoder - service interface for encoding passwords.
     * @return boolean check result
     * @author Mike Ostapiuk
     */
    public boolean comparePasswordLogin(LoginDto loginDto, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(loginDto.getPassword(), findByEmail(loginDto.getEmail()).getPassword())) {
            throw new BadEmailOrPasswordException(ErrorMessage.BAD_EMAIL_OR_PASSWORD);
        }
        return true;
    }

    /**
     * Method that updates refresh token for a given user.
     *
     * @param refreshToken - new refresh token key
     * @return {@link JwtTokensDto}
     * @author Mike Ostapiuk
     */
    public JwtTokensDto updateAccessTokens(String refreshToken) {
        String email;
        try {
            email = jwtTokenProvider.getEmailFromJWT(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new BadRefreshTokenException(REFRESH_TOKEN_NOT_VALID);
        }
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new BadEmailOrPasswordException(ErrorMessage.BAD_EMAIL_OR_PASSWORD));
        if (jwtTokenProvider.isTokenValid(refreshToken, user.getRefreshKey())) {
            return new JwtTokensDto(
                    jwtTokenProvider.generateAccessToken(user.getEmail()),
                    jwtTokenProvider.generateRefreshToken(user.getEmail())
            );
        }
        throw new BadRefreshTokenException(REFRESH_TOKEN_NOT_VALID);
    }

    public Long countAllActiveUsers(){
        return userRepository.countAllActiveUsers();
    }

    public Long countAllActiveUsersByRole(UserRole role) {
        return userRepository.countAllActiveUsersByRole(role);
    }

    public Long countAllUsersByRegistrationDateBetween(Date start, Date end) {
        return
                userRepository.countAllByRegistrationDateBetween(start, end);
    }

    public List<User> findAllByRole(UserRole role) {
        return userRepository.findAllActiveUsersByRole(role);
    }

    public Long countAllActiveByRoleAndRegistrationDateBefore(UserRole role, Date date) {
        return userRepository.countAllActiveByRoleAndRegistrationDateBefore(role,date);
    }
}