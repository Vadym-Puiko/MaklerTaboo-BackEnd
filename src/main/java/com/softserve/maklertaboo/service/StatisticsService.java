package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.repository.FlatRepository;
import com.softserve.maklertaboo.repository.comment.FlatCommentRepository;
import com.softserve.maklertaboo.repository.comment.UserCommentRepository;
import com.softserve.maklertaboo.repository.request.RequestForFlatVerificationRepository;
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
    private final FlatRepository flatRepository;
    private final RequestForFlatVerificationRepository requestFlatRepository;
    private final UserCommentRepository userCommentRepository;
    private final FlatCommentRepository flatCommentRepository;


    public StatisticsService(UserService userService,
                             FlatRepository flatRepository,
                             RequestForFlatVerificationRepository requestFlatRepository,
                             UserCommentRepository userCommentRepository,
                             FlatCommentRepository flatCommentRepository) {
        this.userService = userService;
        this.flatRepository = flatRepository;
        this.requestFlatRepository = requestFlatRepository;
        this.userCommentRepository = userCommentRepository;
        this.flatCommentRepository = flatCommentRepository;
    }

    public Long countActiveFlats() {
        return flatRepository.countAllByIsActive(true);
    }

    public Long countUnactiveFlats() {
        return flatRepository.countAllByIsActive(false);
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
        return userCommentRepository.countAllByIsActiveTrue();
    }

    public Long countActiveFlatsComments() {
        return flatCommentRepository.countAllByIsActiveTrue();
    }

    public Long countUsersRegisteredOnDay(LocalDate day) {
        LocalDateTime endOfDay = day.atStartOfDay().plusDays(1);
        return userService.countAllUsersByRegistrationDateBetween(asDate(endOfDay.minusDays(1)), asDate(endOfDay));
    }

    public Long countFlatsPostedOnDay(LocalDate day) {
        LocalDateTime endOfDay = day.atStartOfDay().plusDays(1);
        return requestFlatRepository.countAllVerificationDateBetweenAndStatusIsApproved(
                asDate(endOfDay.minusDays(1)), asDate(endOfDay));
    }

    public Long countFlatsPostedBetweenDates(LocalDate start, LocalDate end) {
        return requestFlatRepository.countAllVerificationDateBetweenAndStatusIsApproved(asDate(start), asDate(end));
    }

    public Long countUsersRegisteredBetweenDates(LocalDate start, LocalDate end) {
        return userService.countAllUsersByRegistrationDateBetween(asDate(start), asDate(end));
    }

    public Long countCommentsPostedBetweenDates(LocalDate start, LocalDate end) {
        return flatCommentRepository.countAllByPublicationDateBetween(asLocalDateTime(start), asLocalDateTime(end)) +
                userCommentRepository.countAllByPublicationDateBetween(asLocalDateTime(start), asLocalDateTime(end));
    }

    public List<User> getLandlordsSortedByFlatCountLimit(int limit) {
        return userService.findAllByRole(UserRole.ROLE_LANDLORD).stream()
                .sorted(Comparator.comparingLong(flatRepository::countAllByOwner).reversed())
                .limit(limit).collect(Collectors.toList());
    }

    public Long countFlatsByOwner(Long id) {
        User user = userService.findById(id);
        return flatRepository.countAllByOwner(user);
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
        return requestFlatRepository.countAllVerificationDateBeforeAndStatusIsApproved(asDate(month));
    }

    public Long countFlatCommentsPostedBeforeMonth(LocalDate month) {
        return flatCommentRepository.countAllByPublicationDateBefore(
                asLocalDateTime(month.withDayOfMonth(month.getMonth().length(month.isLeapYear()))));
    }

    public Long countUserCommentsPostedBeforeMonth(LocalDate month) {
        return userCommentRepository.countAllByPublicationDateBefore(
                asLocalDateTime(month.withDayOfMonth(month.getMonth().length(month.isLeapYear()))));
    }
}