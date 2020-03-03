package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.passport.PassportDto;
import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.service.PassportService;
import com.softserve.maklertaboo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin
@RestController
public class PassportController {

    private final PassportService passportService;
    private final UserService userService;

    @Autowired
    public PassportController(PassportService passportService, UserService userService) {
        this.passportService = passportService;
        this.userService = userService;
    }

    @GetMapping("/passport")
    public List<PassportDto> getPassportData() {
        return passportService.findAll();
    }

    @GetMapping("/user")
    public List<UserDto> getUserData() {
        return userService.findAllUser();
    }
}
