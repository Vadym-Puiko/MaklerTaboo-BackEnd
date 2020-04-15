package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.constant.HttpStatuses;
import com.softserve.maklertaboo.dto.request.RequestForBanFlatDto;
import com.softserve.maklertaboo.mapping.request.RequestForFlatMapper;
import com.softserve.maklertaboo.mapping.request.RequestForUserMapper;
import com.softserve.maklertaboo.service.FlatService;
import com.softserve.maklertaboo.service.RequestForVerificationService;
import com.softserve.maklertaboo.service.StatisticsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.softserve.maklertaboo.utils.DateUtils.asDate;

@CrossOrigin
@RestController
@PreAuthorize("hasRole ('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
@RequestMapping("/admin")
public class AdminController {

    RequestForVerificationService requestForVerificationService;
    RequestForFlatMapper requestForFlatMapper;
    RequestForUserMapper requestForUserMapper;
    StatisticsService statisticsService;
    FlatService flatService;

    @Autowired
    public AdminController(RequestForVerificationService requestForVerificationService,
                           RequestForUserMapper requestForUserMapper,
                           RequestForFlatMapper requestForFlatMapper,
                           StatisticsService statisticsService,
                           FlatService flatService) {
        this.requestForVerificationService = requestForVerificationService;
        this.requestForFlatMapper = requestForFlatMapper;
        this.requestForUserMapper = requestForUserMapper;
        this.statisticsService = statisticsService;
        this.flatService = flatService;
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

    /**
     * The method which return list of posts by page.
     *
     * @param page,size {@link Integer} for pageable configuration.
     * @param status {@link String}.
     * @return {@link ResponseEntity} with page of {@link RequestForBanFlatDto}
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Get all posts by page")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 303, message = HttpStatuses.SEE_OTHER),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @GetMapping("/all/posts")
    public Page<RequestForBanFlatDto> getAllPosts(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                  @RequestParam(value = "status") String status) {
        Pageable pageable = PageRequest.of(page, size);
        return requestForVerificationService.getAllActivePublication(pageable, status);
    }

    /**
     * The method which activated flat.
     *
     * @param requestForBanFlatDto {@link RequestForBanFlatDto}.
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Request for activated flat")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @PutMapping("/flat/activate")
    public void requestForActivateFlat(@RequestBody RequestForBanFlatDto requestForBanFlatDto) {
        requestForVerificationService.activatePublication(requestForBanFlatDto);
    }

    /**
     * The method which deactivated flat.
     *
     * @param requestForBanFlatDto {@link RequestForBanFlatDto}.
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Request for deactivated flat")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @PutMapping("/flat/deactivate")
    public void requestForDeactivateFlat(@RequestBody RequestForBanFlatDto requestForBanFlatDto) {
        requestForVerificationService.deactivatePublication(requestForBanFlatDto);
    }

    /**
     * The method for deleting flat by ID.
     *
     * @param id for deleting
     * @return {@link ResponseEntity} with {@link Long}
     * @author Vadym Puiko
     */
    @ApiOperation(value = "Delete flat by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
    })
    @DeleteMapping("/flat/{id}")
    public ResponseEntity<Long> deleteFlat(@PathVariable Long id) {
        flatService.deleteFlat(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
