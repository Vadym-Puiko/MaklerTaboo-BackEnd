package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.mapping.request.RequestForFlatMapper;
import com.softserve.maklertaboo.mapping.request.RequestForUserMapper;
import com.softserve.maklertaboo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.softserve.maklertaboo.utils.DateUtils.asDate;

@CrossOrigin
@RestController
@PreAuthorize("hasRole ('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
@RequestMapping("/admin")
public class StatisticsController {

    RequestForFlatMapper requestForFlatMapper;
    RequestForUserMapper requestForUserMapper;
    StatisticsService statisticsService;
    UserMapper userMapper;

    @Autowired
    public StatisticsController(RequestForUserMapper requestForUserMapper,
                                RequestForFlatMapper requestForFlatMapper,
                                StatisticsService statisticsService,
                                UserMapper userMapper) {
        this.requestForFlatMapper = requestForFlatMapper;
        this.requestForUserMapper = requestForUserMapper;
        this.statisticsService = statisticsService;
        this.userMapper = userMapper;
    }

    @GetMapping("statistics/active-flats")
    public Long getNumberOfActiveFlats() {
        return statisticsService.getNumberOfActiveFlats();
    }

    @GetMapping("statistics/active-users")
    public Long getNumberOfActiveUsers() {
        return statisticsService.getNumberOfActiveUsers();
    }

    @GetMapping("statistics/active-landlords")
    public Long getNumberOfActiveLandlords() {
        return statisticsService.getCountOfActiveLandlords();
    }


    @GetMapping("statistics/count-comments")
    public List<Long> getNumberOfActiveComments() {
        return Arrays.asList(statisticsService.getCountOfActiveFlatsComments(),
                statisticsService.getCountOfActiveUsersComments());
    }


    @GetMapping("statistics/users-landlords")
    public List<Long> getCountOfUsersByRole() {
        return Arrays.asList(statisticsService.getCountOfActiveRenters(),
                statisticsService.getCountOfActiveLandlords(),
                statisticsService.getCountOfActiveModerators());
    }

    @GetMapping("statistics/active-unactive-flats")
    public List<Long> getCountOfActiveUnactiveFlats() {
        return Arrays.asList(statisticsService.getNumberOfActiveFlats(),
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

    @GetMapping(value = "statistics/count-posted-flats", params = {"start", "end"})
    public Long getCountOfFlatsBetween(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        return statisticsService.getCountOfFlatsBetween(start, end);
    }

    @GetMapping(value = "statistics/count-posted-comments", params = {"start", "end"})
    public Long getCountOfCommentsBetween(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        return statisticsService.getCountofPostedComments(start, end);
    }

    @GetMapping(value = "statistics/get-top-landlords", params = {"number"})
    public List<UserDto> getToplandlords(@RequestParam(defaultValue = "8") Integer number) {
        return statisticsService.getTopLandlords(number).stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "statistics/get-flat-count-of-user", params = {"id"})
    public Long getFlatsCountOfUser(@RequestParam Long id) {
        return statisticsService.getFlatsCountOfUser(id);
    }
}
