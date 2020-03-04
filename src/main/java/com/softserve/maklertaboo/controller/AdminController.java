package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.request.RequestForFlatDto;
import com.softserve.maklertaboo.dto.request.RequestForUserDto;
import com.softserve.maklertaboo.entity.request.RequestForFlatVerification;
import com.softserve.maklertaboo.entity.request.RequestForUserVerification;
import com.softserve.maklertaboo.mapping.request.RequestForFlatMapper;
import com.softserve.maklertaboo.mapping.request.RequestForUserMapper;
import com.softserve.maklertaboo.service.RequestForVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<RequestForFlatDto> getRequestsForFlats() {
        return requestForVerificationService
                .getAllRequestsForFlatVerification().stream()
                .map(requestForFlatMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/requests/user")
    public List<RequestForUserDto> getRequestsForUsers() {
        return requestForVerificationService
                .getAllRequestsForUserVerification().stream()
                .map(requestForUserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("requests/flat/{id}/approve")
    public void approveRequestForFlat(@PathVariable Long id) {
        requestForVerificationService.approveFlatRequest(id);
    }

    @PutMapping("requests/user/{id}/approve")
    public void approveRequestForUser(@PathVariable Long id) {
        requestForVerificationService.approveUserRequest(id);
    }

}
