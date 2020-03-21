package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.passport.PassportDto;
import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.service.PassportService;
import com.softserve.maklertaboo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/passport")
@Validated
public class PassportController {

    private final PassportService passportService;
    private final UserService userService;

    @Autowired
    public PassportController(PassportService passportService, UserService userService) {
        this.passportService = passportService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public  PassportDto getPassportData(@PathVariable Long id) {
        return passportService.getPassport(id);
    }

    @PostMapping("/{id}")
    public void updatePassportData(@PathVariable Long id, @RequestBody @Valid PassportDto passportDto) {
        UserDto userDto = userService.findUserById(id);
        passportService.updatePassport(userDto, passportDto);
    }

}
