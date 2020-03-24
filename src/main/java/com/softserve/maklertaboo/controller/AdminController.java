package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.request.RequestForFlatDto;
import com.softserve.maklertaboo.dto.request.RequestForUserDto;
import com.softserve.maklertaboo.mapping.request.RequestForFlatMapper;
import com.softserve.maklertaboo.mapping.request.RequestForUserMapper;
import com.softserve.maklertaboo.service.RequestForVerificationService;
import com.softserve.maklertaboo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@PreAuthorize("ROLE_ADMIN")
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


    @GetMapping("statistics/count-comments")
    public List<Long> getCountOfActiveComments() {
        return Arrays.asList(statisticsService.getCountOfActiveUsersComments(),
                             statisticsService.getCountOfActiveFlatsComments());
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

    @GetMapping("statistics/user-registration-dynamics/{days}")
    public List<Long> getCountOfRegisteredUsersForLastDays(@PathVariable("days") int days) {
        return statisticsService.getCountOfRegisteredUsersForLastDays(days);
    }

    @GetMapping("statistics/flat-creation-dynamics/{days}")
    public List<Long> getCountOfCreatedFlatsForLastDays(@PathVariable("days") int days) {
        return statisticsService.getCountOfPostedFlatsForLastDays(days);
    }

    @GetMapping("statistics/users-dynamics/{fromMonth}/{toMonth}")
    public List<Long> getCountOfUsersForMount(@PathVariable("fromMonth") String fromMonth,
                                              @PathVariable("toMonth") String toMonth) {
        return statisticsService.getCountOfUsersForBetweenMonths(asDate(fromMonth), asDate(toMonth));
    }

    @GetMapping("statistics/landlords-dynamics/{fromMonth}/{toMonth}")
    public List<Long> getCountOfLandlordsForMount(@PathVariable("fromMonth") String fromMonth,
                                                  @PathVariable("toMonth") String toMonth) {
        return statisticsService.getCountOfLandlordsForBetweenMonths(asDate(fromMonth), asDate(toMonth));
    }

    @GetMapping("statistics/month-names/{fromMonth}/{toMonth}")
    public List<String> getNameOfMonthsInRange(@PathVariable("fromMonth") String fromMonth,
                                               @PathVariable("toMonth") String toMonth) {
        return statisticsService.getNameOfMonthsInRange(asDate(fromMonth), asDate(toMonth));
    }


    @GetMapping("statistics/user-comments-dynamics/{days}")
    public List<Long> getCountOfUserCommentsForLastDays(@PathVariable("days") int days) {
        return statisticsService.getCountOfPostedUsersCommentsFlatsLastDays(days);
    }

    @GetMapping("statistics/flat-comments-dynamics/{days}")
    public List<Long> getCountOfFlatCommentsForLastDays(@PathVariable("days") int days) {
        return statisticsService.getCountOfPostedFlatsCommentsFlatsLastDays(days);
    }

    private Date asDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date date1 = null;
        try {
            date1 = formatter.parse(date);
        } catch (ParseException e) {

        }
        return date1;
    }

}
