package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.user.User;

import com.softserve.maklertaboo.mapping.UserMapper;

import com.softserve.maklertaboo.repository.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public void saveUser(UserDto userDto) {
        User user = userMapper.convertToEntity(userDto);
        userRepository.save(user);
    }

    public List<UserDto> findAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return userMapper.convertToDto(user);
    }

    public UserDto findByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return userMapper.convertToDto(user);
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
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

    public void updateUserPhoto(Long id, String photo) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        user.setPhotoUrl(photo);
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

}
