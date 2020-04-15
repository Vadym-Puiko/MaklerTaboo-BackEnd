package com.softserve.maklertaboo.controller.statistics;


import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@PreAuthorize("hasRole ('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
@RequestMapping("/admin/user-statistics")
public class UserStatisticsController {

    private StatisticsService statisticsService;
    private UserMapper userMapper;

    @Autowired
    public UserStatisticsController(StatisticsService statisticsService,
                                    UserMapper userMapper) {
        this.statisticsService = statisticsService;
        this.userMapper = userMapper;
    }

    @GetMapping("count-active-users")
    public Long countActiveUsers() {
        return statisticsService.countActiveUsers();
    }

    @GetMapping("count-active-renters")
    public Long countActiveRenters() {
        return statisticsService.countActiveRenters();
    }

    @GetMapping("count-active-landlords")
    public Long countActiveLandlords() {
        return statisticsService.countActiveLandlords();
    }

    @GetMapping("count-active-moderators")
    public Long countActiveModerators() {
        return statisticsService.countActiveModerators();
    }

    @GetMapping(value = "count-registered-users-on-day", params = {"day"})
    public Long countUsersRegisteredOnDay(@RequestParam("day")
                                          @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate day) {
        return statisticsService.countUsersRegisteredOnDay(day);
    }

    @GetMapping(value = "count-registered-renters-before-month", params = {"month"})
    public Long countRentersRegisteredBeforeMonth(@RequestParam("month")
                                                  @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate month) {
        return statisticsService.countRentersRegisteredBeforeMonth(month);
    }

    @GetMapping(value = "count-registered-landlords-before-month", params = {"month"})
    public Long countLandlordsRegisteredBeforeMonth(@RequestParam("month")
                                                    @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate month) {
        return statisticsService.countLandlordsRegisteredBeforeMonth(month);
    }

    @GetMapping(value = "get-top-landlords", params = {"limit"})
    public List<UserDto> getLandlordsSortedByFlatCountLimit(@RequestParam(name = "limit", defaultValue = "8") Integer limit) {
        return statisticsService.getLandlordsSortedByFlatCountLimit(limit).stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "count-users-registered-between-dates", params = {"start", "end"})
    public Long countUsersRegisteredBetweenDates(@RequestParam("start") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate start,
                                                 @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate end) {
        return statisticsService.countUsersRegisteredBetweenDates(start, end);
    }

    @GetMapping(value = "count-all-commitments-between-dates", params = {"start", "end"})
    public Long countAllCommitmentsBetweenDates(@RequestParam("start") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate start,
                                                @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate end) {
        return statisticsService.countAllCommitmentsBetweenDates(start, end);
    }

    @GetMapping(value = "count-commitments-of-landlord", params = {"userId"})
    public Long countCommitmentsOfLandlord(@RequestParam("userId") Long id) {
        return statisticsService.countCommitmentsOfLandlord(id);
    }

}
