package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.user.UserAccountDto;
import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.User;

import com.softserve.maklertaboo.mapping.UserMapper;

import com.softserve.maklertaboo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void save(UserDto userDto) {
        User user = userMapper.convertToEntity(userDto);
        userRepository.save(user);
    }

    public List<UserAccountDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public void update(Long id, UserAccountDto userAccountDto) {
        User user = findById(id);
        user.setUsername(userAccountDto.getUsername());
        user.setEmail(userAccountDto.getEmail());
        user.setPhoneNumber(userAccountDto.getPhoneNumber());
        user.setPhotoUrl(userAccountDto.getPhotoUrl());
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
