package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.enums.RequestForVerificationType;
import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.repository.FlatRepository;
import com.softserve.maklertaboo.repository.comment.FlatCommentRepository;
import com.softserve.maklertaboo.repository.comment.UserCommentRepository;
import com.softserve.maklertaboo.repository.request.RequestForFlatVerificationRepository;
import com.softserve.maklertaboo.repository.request.RequestForUserVerificationRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public Long getCountOfActiveFlats() {
        return flatRepository.countAllByIsActive(true);
    }

    public Long getCountOfUnactiveFlats() {
        return flatRepository.countAllByIsActive(false);
    }

    public Long getCountOfActiveUsers() {
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

    public Long getCountOfRegisteredUsersByDay(LocalDate day) {
        LocalDateTime endOfDay = day.atStartOfDay().plusDays(1);
        return userRepository.countAllByRegistrationDateBetween(asDate(endOfDay.minusDays(1)), asDate(endOfDay));
    }


    public List<Long> getCountOfPostedFlatsForLastDays(int numberOfDays) {
        LocalDate date = LocalDate.now().minusDays(numberOfDays);
        return IntStream.rangeClosed(1, numberOfDays)
                .mapToObj(date::plusDays)
                .map(this::getCountOfPostedFlatsByDay)
                .collect(Collectors.toList());
    }

    public Long getCountOfPostedFlatsByDay(LocalDate day) {
        LocalDateTime endOfDay = day.atStartOfDay().plusDays(1);
        return requestFlatRepository.countAllVerificationDateBetweenAndStatusIsApproved(asDate(endOfDay.minusDays(1)), asDate(endOfDay));
    }


    public List<Long> getCountOfUsersForBetweenMonths(Date from, Date to) {
        LocalDate date = asLocalDate(from);
        return IntStream.rangeClosed(0, (int) ChronoUnit.MONTHS.between(asLocalDate(from), asLocalDate(to)))
                .mapToObj(date::plusMonths)
                .map(this::getCountOfUsersBeforeMonth)
                .collect(Collectors.toList());
    }

    public Long getCountOfUsersBeforeMonth(LocalDate month) {
        LocalDate endOfMont = month.with(TemporalAdjusters.lastDayOfMonth());
        return userRepository.countAllByRegistrationDateBefore(asDate(endOfMont));
    }

    public List<Long> getCountOfLandlordsForBetweenMonths(Date from, Date to) {
        LocalDate date = asLocalDate(from);
        return IntStream.rangeClosed(0, (int) ChronoUnit.MONTHS.between(asLocalDate(from), asLocalDate(to)))
                .mapToObj(date::plusMonths)
                .map(this::getCountOfLandlordsBeforeMonth)
                .collect(Collectors.toList());
    }

    public Long getCountOfLandlordsBeforeMonth(LocalDate month) {
        LocalDate endOfMont = month.with(TemporalAdjusters.lastDayOfMonth());
        return requestUserRepository.countAllVerificationDateLessAndStatusIsApproved(asDate(endOfMont), RequestForVerificationType.LANDLORD);
    }


    public List<Long> getCountOfPostedUsersCommentsFlatsLastDays(int numberOfDays) {
        LocalDate date = LocalDate.now().minusDays(numberOfDays);
        return IntStream.rangeClosed(1, numberOfDays)
                .mapToObj(date::plusDays)
                .map(this::getCountOfPostedUsersCommentsFlatsByDay)
                .collect(Collectors.toList());
    }

    public Long getCountOfPostedUsersCommentsFlatsByDay(LocalDate day) {
        LocalDateTime endOfDay = day.atStartOfDay().plusDays(1);
        return userCommentRepository.countAllByPublicationDateBetween(endOfDay.minusDays(1), endOfDay);
    }

    public List<Long> getCountOfPostedFlatsCommentsFlatsLastDays(int numberOfDays) {
        LocalDate date = LocalDate.now().minusDays(numberOfDays);
        return IntStream.rangeClosed(1, numberOfDays)
                .mapToObj(date::plusDays)
                .map(this::getCountOfPostedFlatsCommentsFlatsByDay)
                .collect(Collectors.toList());
    }

    public Long getCountOfPostedFlatsCommentsFlatsByDay(LocalDate day) {
        LocalDateTime endOfDay = day.atStartOfDay().plusDays(1);
        return flatCommentRepository.countAllByPublicationDateBetween(endOfDay.minusDays(1), endOfDay);
    }


    public List<String> getNameOfMonthsInRange(Date from, Date to) {
        LocalDate date = asLocalDate(from);
        return IntStream.rangeClosed(0, (int) ChronoUnit.MONTHS.between(asLocalDate(from), asLocalDate(to)))
                .mapToObj(date::plusMonths)
                .map(LocalDate::getMonth)
                .map(Objects::toString)
                .collect(Collectors.toList());
    }

    private Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private LocalDate asLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private LocalDateTime asLocalDateTime(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.now());
    }
}