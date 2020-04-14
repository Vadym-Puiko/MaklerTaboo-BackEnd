package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.mapping.request.RequestForFlatMapper;
import com.softserve.maklertaboo.mapping.request.RequestForUserMapper;
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
@RequestMapping("/admin/statistics")
public class StatisticsController {

//    RequestForFlatMapper requestForFlatMapper;
//    RequestForUserMapper requestForUserMapper;
//    StatisticsService statisticsService;
//    UserMapper userMapper;
//
//    @Autowired
//    public StatisticsController(RequestForUserMapper requestForUserMapper,
//                                RequestForFlatMapper requestForFlatMapper,
//                                StatisticsService statisticsService,
//                                UserMapper userMapper) {
//        this.requestForFlatMapper = requestForFlatMapper;
//        this.requestForUserMapper = requestForUserMapper;
//        this.statisticsService = statisticsService;
//        this.userMapper = userMapper;
//    }
//
//    @GetMapping("count-active-flats")
//    public Long countActiveFlats() {
//        return statisticsService.countActiveFlats();
//    }
//
//    @GetMapping("count-active-users")
//    public Long countActiveUsers() {
//        return statisticsService.countActiveUsers();
//    }
//
//    @GetMapping("count-active-landlords")
//    public Long countActiveLandlords() {
//        return statisticsService.countActiveLandlords();
//    }
//
//
//    @GetMapping("statistics/count-active-comments")
//    public List<Long> countActiveComments() {
//        return Arrays.asList(statisticsService.countActiveFlatsComments(),
//                statisticsService.countActiveUsersComments());
//    }
//
//    @GetMapping("statistics/users-landlords")
//    public List<Long> getCountOfUsersByRole() {
//        return Arrays.asList(statisticsService.countActiveRenters(),
//                statisticsService.countActiveLandlords(),
//                statisticsService.countActiveModerators());
//    }
//
//    @GetMapping("statistics/active-unactive-flats")
//    public List<Long> getCountOfActiveUnactiveFlats() {
//        return Arrays.asList(statisticsService.countActiveFlats(),
//                statisticsService.countUnactiveFlats());
//    }
//
//    @GetMapping("count-registered-users")
//    public Long countRegisteredUsersByDay(@RequestParam("day")
//                                          @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate day) {
//        return statisticsService.countRegisteredUsersByDay(day);
//    }
//
//    @GetMapping("count-posted-flats")
//    public Long countPostedFlatsByDay(@RequestParam("day")
//                                      @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate day) {
//        return statisticsService.countPostedFlatsByDay(day);
//    }
//
//    @GetMapping("statistics/users-dynamics/{fromMonth}/{toMonth}")
//    public List<Long> getCountOfUsersForMount(@PathVariable("fromMonth") String fromMonth,
//                                              @PathVariable("toMonth") String toMonth) {
//        return statisticsService.getCountOfUsersForBetweenMonths(asDate(fromMonth), asDate(toMonth));
//    }
//
//    @GetMapping("statistics/landlords-dynamics/{fromMonth}/{toMonth}")
//    public List<Long> getCountOfLandlordsForMount(@PathVariable("fromMonth") String fromMonth,
//                                                  @PathVariable("toMonth") String toMonth) {
//        return statisticsService.getCountOfLandlordsForBetweenMonths(asDate(fromMonth), asDate(toMonth));
//    }
//
//    @GetMapping("statistics/month-names/{fromMonth}/{toMonth}")
//    public List<String> getNameOfMonthsInRange(@PathVariable("fromMonth") String fromMonth,
//                                               @PathVariable("toMonth") String toMonth) {
//        return statisticsService.getNameOfMonthsInRange(asDate(fromMonth), asDate(toMonth));
//    }
//
//
//    @GetMapping("statistics/user-comments-dynamics/{days}")
//    public List<Long> getCountOfUserCommentsForLastDays(@PathVariable("days") int days) {
//        return statisticsService.getCountOfPostedUsersCommentsFlatsLastDays(days);
//    }
//
//    @GetMapping("statistics/flat-comments-dynamics/{days}")
//    public List<Long> getCountOfFlatCommentsForLastDays(@PathVariable("days") int days) {
//        return statisticsService.getCountOfPostedFlatsCommentsFlatsLastDays(days);
//    }
//
//    @GetMapping(value = "count-posted-flats", params = {"start", "end"})
//    public Long countPostedFlatsBetweenDates(@RequestParam("start") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate start,
//                                             @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate end) {
//        return statisticsService.countPostedFlatsBetweenDates(start, end);
//    }
//
//    @GetMapping(value = "count-posted-comments", params = {"start", "end"})
//    public Long countPostedCommentsBetweenDates(@RequestParam("start") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate start,
//                                                @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate end) {
//        return statisticsService.countPostedCommentsBetweenDates(start, end);
//    }
//
//    @GetMapping(value = "statistics/get-top-landlords", params = {"number"})
//    public List<UserDto> getLandlordsSortedByFlatCountLimit(@RequestParam(defaultValue = "8") Integer limit) {
//        return statisticsService.getLandlordsSortedByFlatCountLimit(limit).stream()
//                .map(userMapper::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping(value = "statistics/get-flat-count-of-user", params = {"id"})
//    public Long getFlatsCountOfUser(@RequestParam Long id) {
//        return statisticsService.countFlatsByOwner(id);
//    }
}
