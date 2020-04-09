package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.constant.ErrorMessage;
import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.dto.user.UserUpdateDto;
import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.exception.exceptions.*;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.security.dto.JWTSuccessLogInDto;
import com.softserve.maklertaboo.security.dto.JwtTokensDto;
import com.softserve.maklertaboo.security.dto.LoginDto;
import com.softserve.maklertaboo.security.dto.ChangePasswordDto;
import com.softserve.maklertaboo.security.entity.UserDetailsImpl;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.softserve.maklertaboo.constant.ErrorMessage.*;


@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JWTTokenProvider jwtTokenProvider;
    private final AmazonStorageService amazonStorageService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private String endpointUrl;

    @Autowired
    public UserService(UserMapper userMapper,
                       UserRepository userRepository,
                       JWTTokenProvider jwtTokenProvider,
                       AmazonStorageService amazonStorageService,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       @Value("${ENDPOINT_URL}") String endpointUrl) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.amazonStorageService = amazonStorageService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.endpointUrl = endpointUrl;
    }

    public void saveUser(UserDto userDto) {
        User userByName = userRepository.findUserByUsername(userDto.getUsername());
        User userByEmail = userRepository.findUserByEmail(userDto.getEmail());
        User userByPhone = userRepository.findUserByPhoneNumber(userDto.getPhoneNumber());
        if ((userByName != null) || (userByEmail != null) || (userByPhone != null)) {
            throw new UserAlreadyExistsException(ErrorMessage.USER_ALREADY_EXISTS);
        } else {
            User user = userMapper.convertToEntity(userDto);
            userRepository.save(user);
        }
    }

    public Authentication getAuthentication(LoginDto loginDto){
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
    }

    public JWTSuccessLogInDto validateLogin(LoginDto loginDto) {
        User user = userRepository.findUserByEmail(loginDto.getEmail());
        if (user == null) {
            throw new BadEmailOrPasswordException(ErrorMessage.BAD_EMAIL_OR_PASSWORD);
        }
        comparePasswordLogin(loginDto, passwordEncoder);
        return new JWTSuccessLogInDto(user.getId(), user.getUsername(), user.getEmail(), user.getRole().name());
    }

    public boolean comparePasswordLogin(LoginDto loginDto, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(loginDto.getPassword(), findByEmail(loginDto.getEmail()).getPassword())) {
            throw new BadEmailOrPasswordException(ErrorMessage.BAD_EMAIL_OR_PASSWORD);
        }
        return true;
    }

    public List<UserDto> findAllUser() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() != (UserRole.ROLE_ADMIN))
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + id));
        return userMapper.convertToDto(user);
    }

    public UserDto findByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
        }
        return userMapper.convertToDto(user);
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
        }
        return userMapper.convertToDto(user);
    }

    public Page<UserDto> searchUserByUsername(Pageable pageable, String username) {
        return userRepository.findAllByUsernameLike(pageable, "%" + username + "%").map(userMapper::convertToDto);
    }

    public Page<UserDto> searchUserByEmail(Pageable pageable, String email) {
        return userRepository.findAllByEmailLike(pageable, "%" + email + "%").map(userMapper::convertToDto);
    }

    public Page<UserDto> searchUserByPhone(Pageable pageable, String phone) {
        return userRepository.findAllByPhoneNumberLike(pageable, "%" + phone + "%").map(userMapper::convertToDto);
    }

    public void updateUser(UserUpdateDto userUpdateDto) {
        User user = jwtTokenProvider.getCurrentUser();
        user.setUsername(userUpdateDto.getUsername());
        user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        user.setPhotoUrl(userUpdateDto.getPhotoUrl());
        userRepository.save(user);
    }

    public void updateUserIntoAdminPanel(UserUpdateDto userUpdateDto) {
        User user = userRepository.findUserByEmail(userUpdateDto.getEmail());
        user.setUsername(userUpdateDto.getUsername());
        user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        userRepository.save(user);
    }

    public void updatePhoto(MultipartFile multipartFile) {
        User user = jwtTokenProvider.getCurrentUser();
        if (user.getPhotoUrl() != null) {
            amazonStorageService.deleteFile(user.getPhotoUrl());
        }
        String photoName = amazonStorageService.uploadFile(multipartFile);
        user.setPhotoUrl(photoName);
        userRepository.save(user);
    }

    public void deletePhoto() {
        User user = jwtTokenProvider.getCurrentUser();
        amazonStorageService.deleteFile(user.getPhotoUrl());
        user.setPhotoUrl(null);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Boolean emailExists(String email) {
        return userRepository.existsUserByEmail(email);
    }

    public Page<UserDto> findByPage(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::convertToDto);
    }

    public void updateRole(Long userId, UserRole role) {
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        user.setRole(role);
        userRepository.save(user);
    }

    public JwtTokensDto updateAccessTokens(String refreshToken) {
        String email;
        try {
            email = jwtTokenProvider.getEmailFromJWT(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new BadRefreshTokenException(REFRESH_TOKEN_NOT_VALID);
        }
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new BadEmailOrPasswordException(ErrorMessage.BAD_EMAIL_OR_PASSWORD);
        }
        if (jwtTokenProvider.isTokenValid(refreshToken, user.getRefreshKey())) {
            return new JwtTokensDto(
                    jwtTokenProvider.generateAccessToken(user.getEmail()),
                    jwtTokenProvider.generateRefreshToken(user.getEmail())
            );
        }
        throw new BadRefreshTokenException(REFRESH_TOKEN_NOT_VALID);
    }

    @Transactional
    public void changeUserPassword(ChangePasswordDto changePasswordDto) {
        User user = jwtTokenProvider.getCurrentUser();
        if (user == null) {
            throw new BadEmailOrPasswordException(ErrorMessage.BAD_EMAIL_OR_PASSWORD);
        }
        if (!passwordEncoder.matches(changePasswordDto.getPassword(), user.getPassword())) {
            throw new PasswordsDoNotMatchesException(CURRENT_PASSWORD_DOES_NOT_MATCH);
        }
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmNewPassword())) {
            throw new PasswordsDoNotMatchesException(PASSWORDS_DO_NOT_MATCHES);
        }
        userRepository.updatePassword(passwordEncoder.encode(changePasswordDto.getNewPassword()),
                user.getId());
    }

    public UserDto getCurrentUserDto(){
        return userMapper.convertToDto(jwtTokenProvider.getCurrentUser());
    }
}