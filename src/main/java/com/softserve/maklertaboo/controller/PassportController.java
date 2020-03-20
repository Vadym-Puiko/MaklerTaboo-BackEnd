package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.passport.PassportDto;
import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.service.PassportService;
import com.softserve.maklertaboo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PassportController {

    private final PassportService passportService;
    private final UserService userService;

    @Autowired
    public PassportController(PassportService passportService, UserService userService) {
        this.passportService = passportService;
        this.userService = userService;
    }

    @GetMapping("/passport/{id}")
    public PassportDto getPassportData(@PathVariable Long id) {
        return passportService.getPassport(id);
    }

    @RequestMapping(value = "/passport/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updatePassportData(@PathVariable Long id, @RequestBody @Valid PassportDto passportDto) {
        passportService.updatePassport(id, passportDto);
    }

}
