package com.softserve.maklertaboo.controller.statistics;


import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.softserve.maklertaboo.utils.DateUtils.asDate;

@CrossOrigin
@RestController
//@PreAuthorize("hasRole ('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
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

    @GetMapping("count-active-landlords")
    public Long countActiveLandlords() {
        return statisticsService.countActiveLandlords();
    }

    @GetMapping("statistics/users-landlords")
    public List<Long> getCountOfUsersByRole() {
        return Arrays.asList(statisticsService.countActiveRenters(),
                statisticsService.countActiveLandlords(),
                statisticsService.countActiveModerators());
    }

    @GetMapping("count-registered-users")
    public Long countRegisteredUsersByDay(@RequestParam("day")
                                          @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate day) {
        return statisticsService.countRegisteredUsersByDay(day);
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

    @GetMapping(value = "statistics/get-top-landlords", params = {"number"})
    public List<UserDto> getLandlordsSortedByFlatCountLimit(@RequestParam(defaultValue = "8") Integer limit) {
        return statisticsService.getLandlordsSortedByFlatCountLimit(limit).stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

}
