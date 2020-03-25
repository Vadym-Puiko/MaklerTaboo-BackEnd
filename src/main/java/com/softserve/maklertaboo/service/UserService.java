package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.user.JwtTokensDto;
import com.softserve.maklertaboo.constant.ErrorMessage;
import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.exception.exceptions.BadEmailOrPasswordException;
import com.softserve.maklertaboo.exception.exceptions.BadRefreshTokenException;
import com.softserve.maklertaboo.exception.exceptions.UserAlreadyExists;
import com.softserve.maklertaboo.exception.exceptions.UserNotFoundException;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.security.dto.JWTSuccessLogIn;
import com.softserve.maklertaboo.security.dto.LoginDto;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JWTTokenProvider jwtTokenProvider;
    private final AmazonStorageService amazonStorageService;
    private String endpointUrl;

    @Autowired
    public UserService(UserMapper userMapper,
                       UserRepository userRepository,
                       JWTTokenProvider jwtTokenProvider,
                       AmazonStorageService amazonStorageService,
                       @Value("${ENDPOINT_URL}") String endpointUrl) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.amazonStorageService = amazonStorageService;
        this.endpointUrl = endpointUrl;
    }

    public void saveUser(UserDto userDto) {
        User userByName = userRepository.findUserByUsername(userDto.getUsername());
        User userByEmail = userRepository.findUserByEmail(userDto.getEmail());
        User userByPhone = userRepository.findUserByPhoneNumber(userDto.getPhoneNumber());
        if ((userByName != null) || (userByEmail != null) || (userByPhone != null)) {
            throw new UserAlreadyExists(ErrorMessage.USER_ALREADY_EXISTS);
        } else {
            User user = userMapper.convertToEntity(userDto);
            userRepository.save(user);
        }
    }

    public JWTSuccessLogIn validateLogin(LoginDto loginDto) {
        User user = userRepository.findUserByEmail(loginDto.getEmail());
        if (user == null) {
            throw new BadEmailOrPasswordException(ErrorMessage.BAD_EMAIL_OR_PASSWORD);
        }
        return new JWTSuccessLogIn(user.getId(), user.getUsername(), user.getEmail(), user.getRole().name());
    }

    public List<UserDto> findAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + id));
        return userMapper.convertToDto(user);
    }

    public UserDto findByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        UserDto userDto = userMapper.convertToDto(user);
        return userDto;
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return userMapper.convertToDto(user);
    }

    public UserDto findUserByPhoneNumber(String phoneNumber) {
        User user = userRepository.findUserByPhoneNumber(phoneNumber);
        return userMapper.convertToDto(user);
    }

    public void updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPhotoUrl(userDto.getPhotoUrl());
        userRepository.save(user);
    }

    public void updatePhoto(MultipartFile multipartFile, String email) {
        User user = userRepository.findUserByEmail(email);
        if (user.getPhotoUrl() != null) {
            amazonStorageService.deleteFile(user.getPhotoUrl());
        }
        String photoName = amazonStorageService.uploadFile(multipartFile);
        user.setPhotoUrl(photoName);
        userRepository.save(user);
    }

    public void deletePhoto(String email) {
        User user = userRepository.findUserByEmail(email);
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

    public boolean comparePasswordLogin(LoginDto loginDto, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(loginDto.getPassword(), findByEmail(loginDto.getEmail()).getPassword())) {
            throw new BadEmailOrPasswordException(ErrorMessage.BAD_EMAIL_OR_PASSWORD);
        }
        return true;
    }

    @Transactional
    public JwtTokensDto updateAccessTokens(String refreshToken) {
        String email;
        try {
            email = jwtTokenProvider.getEmailFromJWT(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new BadRefreshTokenException("Refresh token is not valid");
        }
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new BadEmailOrPasswordException("Email is not valid");
        }
        userRepository.updateRefreshKey(UUID.randomUUID().toString(), user.getId());
        if (jwtTokenProvider.isTokenValid(refreshToken, user.getRefreshKey())) {
            return new JwtTokensDto(
                    jwtTokenProvider.generateAccessToken(SecurityContextHolder.getContext().getAuthentication()),
                    jwtTokenProvider.generateRefreshToken(SecurityContextHolder.getContext().getAuthentication())
            );
        }
        throw new BadRefreshTokenException("Refresh token is not valid");
    }
}