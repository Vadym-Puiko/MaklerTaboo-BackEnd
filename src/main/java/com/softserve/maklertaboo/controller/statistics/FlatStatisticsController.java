package com.softserve.maklertaboo.controller.statistics;

import com.softserve.maklertaboo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
//@PreAuthorize("hasRole ('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
@RequestMapping("/admin/flat-statistics")
public class FlatStatisticsController {
    private StatisticsService statisticsService;

    @Autowired
    public FlatStatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("count-active-flats")
    public Long countActiveFlats() {
        return statisticsService.countActiveFlats();
    }


    @GetMapping("statistics/active-unactive-flats")
    public List<Long> getCountOfActiveUnactiveFlats() {
        return Arrays.asList(statisticsService.countActiveFlats(),
                statisticsService.countUnactiveFlats());
    }

    @GetMapping("count-posted-flats")
    public Long countPostedFlatsByDay(@RequestParam("day")
                                      @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate day) {
        return statisticsService.countPostedFlatsByDay(day);
    }

    @GetMapping(value = "get-flat-count-of-user", params = {"id"})
    public Long getFlatsCountOfUser(@RequestParam Long id) {
        return statisticsService.countFlatsByOwner(id);
    }

    @GetMapping(value = "count-posted-flats", params = {"start", "end"})
    public Long countPostedFlatsBetweenDates(@RequestParam("start") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate start,
                                             @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate end) {
        return statisticsService.countPostedFlatsBetweenDates(start, end);
    }
}
