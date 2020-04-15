package com.softserve.maklertaboo.controller.statistics;

import com.softserve.maklertaboo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
    public Long countPostedCommentsBetweenDates(@RequestParam("start") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate start,
                                                @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate end) {
        return statisticsService.countPostedCommentsBetweenDates(start, end);
    }

    @GetMapping("statistics/flat-comments-dynamics/{days}")
    public List<Long> getCountOfFlatCommentsForLastDays(@PathVariable("days") int days) {
        return statisticsService.getCountOfPostedFlatsCommentsFlatsLastDays(days);
    }

    @GetMapping("statistics/user-comments-dynamics/{days}")
    public List<Long> getCountOfUserCommentsForLastDays(@PathVariable("days") int days) {
        return statisticsService.getCountOfPostedUsersCommentsFlatsLastDays(days);
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
