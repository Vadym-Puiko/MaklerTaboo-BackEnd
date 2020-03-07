package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.request.RequestForFlatDto;
import com.softserve.maklertaboo.dto.request.RequestForUserDto;
import com.softserve.maklertaboo.mapping.request.RequestForFlatMapper;
import com.softserve.maklertaboo.mapping.request.RequestForUserMapper;
import com.softserve.maklertaboo.service.RequestForVerificationService;
import com.softserve.maklertaboo.service.StatisticsService;
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
    StatisticsService statisticsService;

    @Autowired
    public AdminController(RequestForVerificationService requestForVerificationService,
                           RequestForUserMapper requestForUserMapper,
                           RequestForFlatMapper requestForFlatMapper,
                           StatisticsService statisticsService) {
        this.requestForVerificationService = requestForVerificationService;
        this.requestForFlatMapper = requestForFlatMapper;
        this.requestForUserMapper = requestForUserMapper;
        this.statisticsService = statisticsService;
    }

    @GetMapping("/requests/flats")
    public List<RequestForFlatDto> getRequestsForFlats() {
        return requestForVerificationService
                .getAllRequestsForFlatVerification().stream()
                .map(requestForFlatMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/requests/users/landlords")
    public List<RequestForUserDto> getRequestsForLandlords() {
        return requestForVerificationService
                .getAllRequestsForLandlordVerification().stream()
                .map(requestForUserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/requests/users/moderators")
    public List<RequestForUserDto> getRequestsForModerators() {
        return requestForVerificationService
                .getAllRequestsForModeratorVerification().stream()
                .map(requestForUserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("requests/flats/{id}/approve")
    public void approveRequestForFlat(@PathVariable Long id) {
        requestForVerificationService.approveFlatRequest(id);
    }

    @PutMapping("requests/flats/{id}/decline")
    public void declineRequestForFlat(@PathVariable Long id) {
        requestForVerificationService.declineFlatRequest(id);
    }

    @PutMapping("requests/users/{id}/approve")
    public void approveRequestForUser(@PathVariable Long id) {
        requestForVerificationService.approveUserRequest(id);
    }

    @PutMapping("requests/users/{id}/decline")
    public void declineRequestForUser(@PathVariable Long id) {
        requestForVerificationService.declineUserRequest(id);
    }

    @GetMapping("statistics/active-flats")
    public Long getCountOfActiveFlats(){
        return statisticsService.getCountOfActiveFlats();
    }

    @GetMapping("statistics/active-users")
    public Long getCountOfActiveUsers(){
        return statisticsService.getCountOfActiveUsers();
    }

    @GetMapping("statistics/active-landlords")
    public Long getCountOfActiveLandlords(){
        return statisticsService.getCountOfActiveLandlords();
    }

}
