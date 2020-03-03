package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.request.RequestForFlatDto;
import com.softserve.maklertaboo.dto.request.RequestForUserDto;
import com.softserve.maklertaboo.mapping.request.RequestForFlatMapper;
import com.softserve.maklertaboo.mapping.request.RequestForUserMapper;
import com.softserve.maklertaboo.service.RequestForVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    RequestForVerificationService requestForVerificationService;
    RequestForFlatMapper requestForFlatMapper;
    RequestForUserMapper requestForUserMapper;

    @Autowired
    public AdminController(RequestForVerificationService requestForVerificationService,
                           RequestForUserMapper requestForUserMapper,
                           RequestForFlatMapper requestForFlatMapper) {
        this.requestForVerificationService = requestForVerificationService;
        this.requestForFlatMapper = requestForFlatMapper;
        this.requestForUserMapper = requestForUserMapper;
    }

    @GetMapping("/requests/flat")
    public List<RequestForFlatDto> getActiveRequestsForFlats() {
        return requestForVerificationService
                .getAllRequestsForFlatVerification().stream()
                .map(requestForFlatMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/requests/user")
    public List<RequestForUserDto> getActiveRequestsForUsers() {
        return requestForVerificationService
                .getAllRequestsForUserVerification().stream()
                .map(requestForUserMapper::convertToDto)
                .collect(Collectors.toList());
    }


}
