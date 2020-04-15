package com.softserve.maklertaboo.controller.statistics;

import com.softserve.maklertaboo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    @GetMapping("count-unactive-flats")
    public Long countUnactiveFlats() {
        return statisticsService.countUnactiveFlats();
    }


    @GetMapping(value = "count-flats-posted-before-month",params = {"month"})
    public Long countFlatsPostedBeforeMonth(@RequestParam("month")
                                            @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate month) {
        return statisticsService.countFlatsPostedBeforeMonth(month);
    }

    @GetMapping("count-posted-flats-on-day")
    public Long countPostedFlatsOnDay(@RequestParam("day")
                                      @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate day) {
        return statisticsService.countFlatsPostedOnDay(day);
    }

    @GetMapping(value = "get-flat-count-of-user", params = {"id"})
    public Long getFlatsCountOfUser(@RequestParam Long id) {
        return statisticsService.countFlatsByOwner(id);
    }

    @GetMapping(value = "count-posted-flats-between-dates", params = {"start", "end"})
    public Long countPostedFlatsBetweenDates(@RequestParam("start") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate start,
                                             @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate end) {
        return statisticsService.countFlatsPostedBetweenDates(start, end);
    }
}
