package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.User;
import com.softserve.maklertaboo.mapping.UserToDto;
import com.softserve.maklertaboo.mapping.UserToEntity;
import com.softserve.maklertaboo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(UserDto userDto) {
        UserToEntity userToEntity = new UserToEntity();
        User user = userToEntity.convertToEntity(userDto);
        userRepository.save(user);
    }

//    public List<UserResponse> findAll() {
//        return userRepository.findAll()
//                .stream()
//                .map(UserResponse::new)
//                .collect(Collectors.toList());
//    }

    public User findOne(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
//
//    public void update(Long id, UserDto userDto) {
//        User user = userToUserRequest(userDto, findOne(id));
//        userRepository.save(user);
//    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}
