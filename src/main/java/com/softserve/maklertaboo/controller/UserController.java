package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.user.UserAccountDto;
import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void save(@RequestBody UserDto userDto) {
        userService.save(userDto);
    }

    @GetMapping
    public List<UserAccountDto> findAll() {
        return userService.findAll();
    }

    @PutMapping
    public void update(Long id, @RequestBody UserAccountDto userAccountDto) {
        userService.update(id, userAccountDto);
    }

    @DeleteMapping
    public void delete(Long id) {
        userService.delete(id);
    }
}

