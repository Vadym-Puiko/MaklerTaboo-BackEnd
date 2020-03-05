package com.softserve.maklertaboo.controller;
import com.softserve.maklertaboo.dto.passport.PassportDto;
import com.softserve.maklertaboo.service.PassportService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PassportController {

    private final PassportService passportService;

    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    @GetMapping("/passport")
    public List<PassportDto> getUsers() {
        return passportService.findAll();
    }
}
