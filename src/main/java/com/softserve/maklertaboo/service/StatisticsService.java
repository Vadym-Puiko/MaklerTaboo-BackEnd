package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.enums.RequestForVerificationType;
import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.repository.FlatRepository;
import com.softserve.maklertaboo.repository.comment.FlatCommentRepository;
import com.softserve.maklertaboo.repository.comment.UserCommentRepository;
import com.softserve.maklertaboo.repository.request.RequestForFlatVerificationRepository;
import com.softserve.maklertaboo.repository.request.RequestForUserVerificationRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.softserve.maklertaboo.utils.DateUtils.*;

@Service
public class StatisticsService {
    private final UserRepository userRepository;
    private final FlatRepository flatRepository;
    private final RequestForFlatVerificationRepository requestFlatRepository;
    private final RequestForUserVerificationRepository requestUserRepository;
    private final UserCommentRepository userCommentRepository;
    private final FlatCommentRepository flatCommentRepository;


    public StatisticsService(UserRepository userRepository, FlatRepository flatRepository, RequestForFlatVerificationRepository requestFlatRepository, RequestForUserVerificationRepository requestUserRepository, UserCommentRepository userCommentRepository, FlatCommentRepository flatCommentRepository) {
        this.userRepository = userRepository;
        this.flatRepository = flatRepository;
        this.requestFlatRepository = requestFlatRepository;
        this.requestUserRepository = requestUserRepository;
        this.userCommentRepository = userCommentRepository;
        this.flatCommentRepository = flatCommentRepository;
    }

    public Long getNumberOfActiveFlats() {
        return flatRepository.countAllByIsActive(true);
    }

    public Long getCountOfUnactiveFlats() {
        return flatRepository.countAllByIsActive(false);
    }

    public Long getNumberOfActiveUsers() {
        return userRepository.count();
    }

    public Long getCountOfActiveRenters() {
        return userRepository.countAllByRole(UserRole.ROLE_RENTER);
    }

    public Long getCountOfActiveLandlords() {
        return userRepository.countAllByRole(UserRole.ROLE_LANDLORD);
    }

    public Long getCountOfActiveModerators() {
        return userRepository.countAllByRole(UserRole.ROLE_MODERATOR);
    }

    public Long getCountOfActiveUsersComments() {
        return userCommentRepository.countAllByIsActiveTrue();
    }

    public Long getCountOfActiveFlatsComments() {
        return flatCommentRepository.countAllByIsActiveTrue();
    }


    public List<Long> getCountOfRegisteredUsersForLastDays(int numberOfDays) {
        LocalDate date = LocalDate.now().minusDays(numberOfDays);
        return IntStream.rangeClosed(1, numberOfDays)
                .mapToObj(date::plusDays)
                .map(this::getCountOfRegisteredUsersByDay)
                .collect(Collectors.toList());
    }


    public List<Long> getCountOfPostedFlatsForLastDays(int numberOfDays) {
        LocalDate date = LocalDate.now().minusDays(numberOfDays);
        return IntStream.rangeClosed(1, numberOfDays)
                .mapToObj(date::plusDays)
                .map(this::getCountOfPostedFlatsByDay)
                .collect(Collectors.toList());
    }


    public List<Long> getCountOfUsersForBetweenMonths(Date from, Date to) {
        LocalDate date = asLocalDate(from);
        return IntStream.rangeClosed(0, monthsBetween(from, to))
                .mapToObj(date::plusMonths)
                .map(this::getCountOfUsersBeforeMonth)
                .collect(Collectors.toList());
    }

    public List<Long> getCountOfLandlordsForBetweenMonths(Date from, Date to) {
        LocalDate date = asLocalDate(from);
        return IntStream.rangeClosed(0, monthsBetween(from, to))
                .mapToObj(date::plusMonths)
                .map(this::getCountOfLandlordsBeforeMonth)
                .collect(Collectors.toList());
    }


    public List<Long> getCountOfPostedUsersCommentsFlatsLastDays(int numberOfDays) {
        LocalDate date = LocalDate.now().minusDays(numberOfDays);
        return IntStream.rangeClosed(1, numberOfDays)
                .mapToObj(date::plusDays)
                .map(this::getCountOfPostedUsersCommentsFlatsByDay)
                .collect(Collectors.toList());
    }


    public List<Long> getCountOfPostedFlatsCommentsFlatsLastDays(int numberOfDays) {
        LocalDate date = LocalDate.now().minusDays(numberOfDays);
        return IntStream.rangeClosed(1, numberOfDays)
                .mapToObj(date::plusDays)
                .map(this::getCountOfPostedFlatsCommentsFlatsByDay)
                .collect(Collectors.toList());
    }


    public List<String> getNameOfMonthsInRange(Date from, Date to) {
        LocalDate date = asLocalDate(from);
        return IntStream.rangeClosed(0, monthsBetween(from, to))
                .mapToObj(date::plusMonths)
                .map(LocalDate::getMonth)
                .map(Objects::toString)
                .collect(Collectors.toList());
    }

    private Long getCountOfPostedFlatsCommentsFlatsByDay(LocalDate day) {
        LocalDateTime endOfDay = day.atStartOfDay().plusDays(1);
        return flatCommentRepository.countAllByPublicationDateBetween(endOfDay.minusDays(1), endOfDay);
    }

    private Long getCountOfRegisteredUsersByDay(LocalDate day) {
        LocalDateTime endOfDay = day.atStartOfDay().plusDays(1);
        return userRepository.countAllByRegistrationDateBetween(asDate(endOfDay.minusDays(1)), asDate(endOfDay));
    }

    private Long getCountOfPostedFlatsByDay(LocalDate day) {
        LocalDateTime endOfDay = day.atStartOfDay().plusDays(1);
        return requestFlatRepository.countAllVerificationDateBetweenAndStatusIsApproved(asDate(endOfDay.minusDays(1)), asDate(endOfDay));
    }

    private Long getCountOfUsersBeforeMonth(LocalDate month) {
        LocalDate endOfMonth = month.with(TemporalAdjusters.lastDayOfMonth());
        return userRepository.countAllByRegistrationDateBefore(asDate(endOfMonth));
    }

    private Long getCountOfLandlordsBeforeMonth(LocalDate month) {
        LocalDate endOfMonth = month.with(TemporalAdjusters.lastDayOfMonth());
        return requestUserRepository.countAllVerificationDateLessAndStatusIsApproved(asDate(endOfMonth), RequestForVerificationType.LANDLORD);
    }

    private Long getCountOfPostedUsersCommentsFlatsByDay(LocalDate day) {
        LocalDateTime endOfDay = day.atStartOfDay().plusDays(1);
        return userCommentRepository.countAllByPublicationDateBetween(endOfDay.minusDays(1), endOfDay);
    }

    public Long getCountOfFlatsBetween(Date start, Date end) {
        return requestFlatRepository.countAllVerificationDateBetweenAndStatusIsApproved(start, end);
    }

    public Long getCountofPostedComments(Date start, Date end) {
        return flatCommentRepository.countAllByPublicationDateBetween(asLocalDateTime(start), asLocalDateTime(end)) +
                userCommentRepository.countAllByPublicationDateBetween(asLocalDateTime(start), asLocalDateTime(end));
    }

    public List<User> getTopLandlords(int limit) {
        return userRepository.findAllByRole(UserRole.ROLE_LANDLORD).stream()
                .sorted(Comparator.comparingLong(flatRepository::countAllByOwner).reversed())
                .limit(limit).collect(Collectors.toList());
    }

    public Long getFlatsCountOfUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(IllegalAccessError::new);
        return flatRepository.countAllByOwner(user);
    }
}