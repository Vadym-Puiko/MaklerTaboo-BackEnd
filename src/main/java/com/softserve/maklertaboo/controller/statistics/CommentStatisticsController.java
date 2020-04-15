package com.softserve.maklertaboo.controller.statistics;

import com.softserve.maklertaboo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin
@RestController
//@PreAuthorize("hasRole ('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
@RequestMapping("/admin/comment-statistics")
public class CommentStatisticsController {

    private StatisticsService statisticsService;

    @Autowired
    public CommentStatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("count-user-comments")
    public Long countActiveUserComments() {
        return statisticsService.countActiveUsersComments();
    }

    @GetMapping("count-flat-comments")
    public Long countActiveFlatComments() {
        return statisticsService.countActiveFlatsComments();
    }

    @GetMapping(value = "count-posted-comments", params = {"start", "end"})
    public Long countCommentsPostedBetweenDates(@RequestParam("start") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate start,
                                                @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate end) {
        return statisticsService.countCommentsPostedBetweenDates(start, end);
    }

    @GetMapping(value = "count-flat-comments-posted-before-month", params = {"month"})
    public Long countFlatCommentsPostedBeforeMonth(@RequestParam("month")
                                                   @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate month) {
        return statisticsService.countFlatCommentsPostedBeforeMonth(month);
    }

    @GetMapping(value = "count-user-comments-posted-before-month", params = {"month"})
    public Long countUserCommentsPostedBeforeMonth(@RequestParam("month")
                                                   @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate month) {
        return statisticsService.countUserCommentsPostedBeforeMonth(month);
    }

}
