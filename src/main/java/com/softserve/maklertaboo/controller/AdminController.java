package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.request.RequestForFlatDto;
import com.softserve.maklertaboo.dto.request.RequestForUserDto;
import com.softserve.maklertaboo.mapping.request.RequestForFlatMapper;
import com.softserve.maklertaboo.mapping.request.RequestForUserMapper;
import com.softserve.maklertaboo.service.RequestForVerificationService;
import com.softserve.maklertaboo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
    public Long getCountOfActiveFlats() {
        return statisticsService.getCountOfActiveFlats();
    }

    @GetMapping("statistics/active-users")
    public Long getCountOfActiveUsers() {
        return statisticsService.getCountOfActiveUsers();
    }

    @GetMapping("statistics/active-landlords")
    public Long getCountOfActiveLandlords() {
        return statisticsService.getCountOfActiveLandlords();
    }

    @GetMapping("statistics/users-landlords")
    public List<Long> getCountOfUsersByRole() {
        return Arrays.asList(statisticsService.getCountOfActiveRenters(),
                statisticsService.getCountOfActiveLandlords(),
                statisticsService.getCountOfActiveModerators());
    }

    @GetMapping("statistics/active-unactive-flats")
    public List<Long> getCountOfActiveUnactiveFlats() {
        return Arrays.asList(statisticsService.getCountOfActiveFlats(),
                statisticsService.getCountOfUnactiveFlats());
    }

    @GetMapping("statistics/user-regisration-dynamics")
    public List<Long> getCountOfRegisteredUsersForLastWeek() {
        return statisticsService.getCountOfRegisteredUsersForLastDays(7);
    }

    @GetMapping("statistics/flat-creation-dynamics")
    public List<Long> getCountOfCreatedFlatsForLastWeek() {
        return statisticsService.getCountOfPostedFlatsForLastDays(7);
    }

    @GetMapping("statistics/users-dynamics/{fromMonth}/{toMonth}")
    public List<Long> getCountOfUsersForMount(@PathVariable("fromMonth") date fromMonth, @PathVariable("toMonth") int toMonth) {
        return statisticsService.getCountOfUsersForBetweenMonths(fromMonth, toMonth);
    }

    @GetMapping("statistics/landlords-dynamics/{fromMonth}/{toMonth}")
    public List<Long> getCountOfLandlordsForMount(@PathVariable("fromMonth") int fromMonth, @PathVariable("toMonth") int toMonth) {
        return statisticsService.getCountOfLandlordsForBetweenMonths(fromMonth, toMonth);
    }

    @GetMapping("statistics/month-names/{fromMonth}/{toMonth}")
    public List<String> getNameOfMonthsInRange(@PathVariable("fromMonth") int fromMonth, @PathVariable("toMonth") int toMonth) {
        return statisticsService.getNameOfMonthsInRange(fromMonth, toMonth);
    }


    @GetMapping("statistics/user-comments-dynamics")
    public List<Long> getCountOfUserCommentsForMount() {
        return statisticsService.getCountOfPostedUserCommentsForLastMonths(7);
    }

    @GetMapping("statistics/flat-comments-dynamics")
    public List<Long> getCountOfFlatCommentsForMount() {
        return statisticsService.getCountOfPostedFlatCommentsForLastMonths(7);
    }
}
