package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.passport.PassportDto;
import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import com.softserve.maklertaboo.service.PassportService;
import com.softserve.maklertaboo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/passport")
public class PassportController {

    private final PassportService passportService;
    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public PassportController(PassportService passportService, UserService userService, JWTTokenProvider jwtTokenProvider) {
        this.passportService = passportService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/getPassport")
    public  PassportDto getPassportData(@RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmailFromJWT(token);
        UserDto userDto = userService.findByEmail(email);
        return passportService.getPassport(userDto.getId());
    }

    @PostMapping("/update")
    public void updatePassportData(@RequestHeader("Authorization") String token, @RequestBody @Valid PassportDto passportDto) {
        String email = jwtTokenProvider.getEmailFromJWT(token);
        UserDto userDto = userService.findByEmail(email);
        passportService.updatePassport(userDto, passportDto);
    }

    @PostMapping("/landlord")
    public void EvaluateToLandlord(@RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmailFromJWT(token);
        UserDto userDto = userService.findByEmail(email);
        passportService.getAdminApproval(userDto);
    }

}
