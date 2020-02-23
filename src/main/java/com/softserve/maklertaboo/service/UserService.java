package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.request.UserRequest;
import com.softserve.maklertaboo.dto.response.UserResponse;
import com.softserve.maklertaboo.entity.User;
import com.softserve.maklertaboo.repository.photo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(UserRequest userRequest) {
        User user = userToUserRequest(userRequest, null);
        userRepository.save(user);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public User findOne(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public void update(Long id, UserRequest userRequest) {
        User user = userToUserRequest(userRequest, findOne(id));
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private User userToUserRequest(UserRequest userRequest, User user) {
        if (user == null) {
            user = new User();
        }
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setPhotoUrl(userRequest.getPhotoUrl());
        return user;
    }
}
