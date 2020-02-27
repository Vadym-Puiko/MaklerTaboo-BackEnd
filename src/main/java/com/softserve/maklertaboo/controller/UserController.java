package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public void save(@RequestBody UserDto userDto) {
        userService.save(userDto);
    }
//
//    @GetMapping
//    public List<UserResponse> findAll() {
//        return userService.findAll();
//    }

//    @PutMapping
//    public void update(Long id, @RequestBody UserRequest request) {
//        userService.update(id, request);
//    }

    @DeleteMapping
    public void delete(Long id) {
        userService.delete(id);
    }
}

