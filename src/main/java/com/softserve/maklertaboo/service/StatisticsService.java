package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.softserve.maklertaboo.utils.DateUtils.asDate;
import static com.softserve.maklertaboo.utils.DateUtils.asLocalDateTime;

@Service
public class StatisticsService {
    private final UserService userService;
    private final FlatService flatService;
    private final RequestForVerificationService requestService;
    private final UserCommentService userCommentService;
    private final FlatCommentService flatCommentService;


    public StatisticsService(UserService userService,
                             FlatService flatService,
                             RequestForVerificationService requestFlatService,
                             UserCommentService userCommentService,
                             FlatCommentService flatCommentService) {
        this.userService = userService;
        this.flatService = flatService;
        this.requestService = requestFlatService;
        this.userCommentService = userCommentService;
        this.flatCommentService = flatCommentService;
    }

    public Long countActiveFlats() {
        return flatService.countAllByIsActive(true);
    }

    public Long countUnactiveFlats() {
        return flatService.countAllByIsActive(false);
    }

    public Long countActiveUsers() {
        return userService.countAllActiveUsers();
    }

    public Long countActiveLandlords() {
        return userService.countAllActiveUsersByRole(UserRole.ROLE_LANDLORD);
    }

    public Long countActiveModerators() {
        return userService.countAllActiveUsersByRole(UserRole.ROLE_MODERATOR);
    }

    public Long countActiveUsersComments() {
        return userCommentService.countAllActiveComments();
    }

    public Long countActiveFlatsComments() {
        return flatCommentService.countAllActiveComments();
    }

    public Long countUsersRegisteredOnDay(LocalDate day) {
        LocalDateTime endOfDay = day.atStartOfDay().plusDays(1);
        return userService.countAllUsersByRegistrationDateBetween(asDate(endOfDay.minusDays(1)), asDate(endOfDay));
    }

    public Long countFlatsPostedOnDay(LocalDate day) {
        LocalDateTime endOfDay = day.atStartOfDay().plusDays(1);
        return requestService.countApprovedFlatRequestsByVerificationDateBetween(
                asDate(endOfDay.minusDays(1)), asDate(endOfDay));
    }

    public Long countFlatsPostedBetweenDates(LocalDate start, LocalDate end) {
        return requestService.countApprovedFlatRequestsByVerificationDateBetween(asDate(start), asDate(end));
    }

    public Long countUsersRegisteredBetweenDates(LocalDate start, LocalDate end) {
        return userService.countAllUsersByRegistrationDateBetween(asDate(start), asDate(end));
    }

    public Long countCommentsPostedBetweenDates(LocalDate start, LocalDate end) {
        return flatCommentService.countAllByPublicationDateBetween(asLocalDateTime(start), asLocalDateTime(end)) +
                userCommentService.countAllByPublicationDateBetween(asLocalDateTime(start), asLocalDateTime(end));
    }

    public List<User> getLandlordsSortedByFlatCountLimit(int limit) {
        return userService.findAllByRole(UserRole.ROLE_LANDLORD).stream()
                .sorted(Comparator.comparingLong(flatService::countAllByOwner).reversed())
                .limit(limit).collect(Collectors.toList());
    }

    public Long countFlatsByOwner(Long id) {
        User user = userService.findById(id);
        return flatService.countAllByOwner(user);
    }

    public Long countRentersRegisteredBeforeMonth(LocalDate month) {
        return userService.countAllActiveByRoleAndRegistrationDateBefore(UserRole.ROLE_RENTER,
                asDate(month.withDayOfMonth(month.getMonth().length(month.isLeapYear()))));
    }

    public Long countLandlordsRegisteredBeforeMonth(LocalDate month) {
        return userService.countAllActiveByRoleAndRegistrationDateBefore(UserRole.ROLE_LANDLORD,
                asDate(month.withDayOfMonth(month.getMonth().length(month.isLeapYear()))));
    }

    public Long countFlatsPostedBeforeMonth(LocalDate month) {
        return requestService.countApprovedFlatRequestsByVerificationDateBefore(asDate(month));
    }

    public Long countFlatCommentsPostedBeforeMonth(LocalDate month) {
        return flatCommentService.countAllByPublicationDateBefore(
                asLocalDateTime(month.withDayOfMonth(month.getMonth().length(month.isLeapYear()))));
    }

    public Long countUserCommentsPostedBeforeMonth(LocalDate month) {
        return userCommentService.countAllByPublicationDateBefore(
                asLocalDateTime(month.withDayOfMonth(month.getMonth().length(month.isLeapYear()))));
    }
}