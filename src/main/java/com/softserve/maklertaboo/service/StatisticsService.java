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
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class StatisticsService<RequestForFlatVerificationService> {
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
        return userRepository.countAllByRole(UserRole.RENTER);
    }

    public Long getCountOfActiveLandlords() {
        return userRepository.countAllByRole(UserRole.LANDLORD);
    }

    public Long getCountOfActiveModerators() {
        return userRepository.countAllByRole(UserRole.MODERATOR);
    }


    public List<Long> getCountOfUsersForWeek() {
        List<Long> countForDay = new ArrayList<>();
        for (int i = -7; i < 0; i++) {
            final Calendar start = Calendar.getInstance();
            start.add(Calendar.DATE, i + 1);

            final Calendar end = Calendar.getInstance();
            end.add(Calendar.DATE, i + 2);

            Date previousDate = Date.from(start.toInstant().truncatedTo(ChronoUnit.DAYS));
            Date nextDate = Date.from(end.toInstant().truncatedTo(ChronoUnit.DAYS));

            countForDay.add(userRepository.countAllByRegistrationDateBetween(previousDate, nextDate));
        }
        return countForDay;
    }


    public List<Long> getCountOfFlatsForWeek() {
        List<Long> countForDay = new ArrayList<>();
        for (int i = -7; i < 0; i++) {
            final Calendar start = Calendar.getInstance();
            start.add(Calendar.DATE, i + 1);

            final Calendar end = Calendar.getInstance();
            end.add(Calendar.DATE, i + 2);

            Date previousDate = Date.from(start.toInstant().truncatedTo(ChronoUnit.DAYS));
            Date nextDate = Date.from(end.toInstant().truncatedTo(ChronoUnit.DAYS));

            countForDay.add(requestFlatRepository.countAllVerificationDateBetweenAndStatus_Approved(previousDate, nextDate));
        }
        return countForDay;
    }


    public List<Long> getCountOfUsersForMounth() {
        List<Long> countForDay = new ArrayList<>();
        for (int i = -7; i < 0; i++) {
            final Calendar start = Calendar.getInstance();
            start.add(Calendar.MONTH, i + 2);

            start.set(Calendar.DAY_OF_MONTH, 1);

            Date previousDate = start.getTime();

            countForDay.add(userRepository.countAllByRegistrationDateBefore(previousDate));
        }
        return countForDay;
    }

    public List<Long> getCountOfLandlordsForMounth() {
        List<Long> countForDay = new ArrayList<>();
        for (int i = -7; i < 0; i++) {
            final Calendar start = Calendar.getInstance();
            start.add(Calendar.MONTH, i + 2);

            start.set(Calendar.DAY_OF_MONTH, 1);

            Date previousDate = start.getTime();

            countForDay.add(requestUserRepository.countAllVerificationDateLessAndStatus_Approved(previousDate, RequestForVerificationType.LANDLORD));
        }
        return countForDay;
    }

    public List<Long> getCountOfUsersCommentsForMounth() {
        List<Long> countForDay = new ArrayList<>();
        for (int i = -7; i < 0; i++) {
            final Calendar start = Calendar.getInstance();
            start.add(Calendar.MONTH, i + 1);
            start.set(Calendar.DAY_OF_MONTH, 1);

            final Calendar end = Calendar.getInstance();
            end.add(Calendar.MONTH, i + 2);
            end.set(Calendar.DAY_OF_MONTH, 1);

            LocalDateTime previousDate = start.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime nextDate = end.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            countForDay.add(userCommentRepository.countAllByPublicationDateBetween(previousDate, nextDate));
        }
        return countForDay;
    }

    public List<Long> getCountOfFlatCommentsForMounth() {
        List<Long> countForDay = new ArrayList<>();
        for (int i = -7; i < 0; i++) {
            final Calendar start = Calendar.getInstance();
            start.add(Calendar.MONTH, i + 1);
            start.set(Calendar.DAY_OF_MONTH, 1);

            final Calendar end = Calendar.getInstance();
            end.add(Calendar.MONTH, i + 2);
            end.set(Calendar.DAY_OF_MONTH, 1);

            LocalDateTime previousDate = start.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime nextDate = end.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            countForDay.add(flatCommentRepository.countAllByPublicationDateBetween(previousDate, nextDate));
        }
        return countForDay;
    }



    private List<String> getLast7Days() {
        LocalDate weekBeforeToday = LocalDate.now().minusDays(7);
        return IntStream.rangeClosed(1, 7)
                .mapToObj(weekBeforeToday::plusDays)
                .map(LocalDate::getDayOfWeek)
                .map(Objects::toString)
                .collect(Collectors.toList());
    }

    private List<String> getLast7Mounths() {
        LocalDate weekBeforeToday = LocalDate.now().minusMonths(7);
        return IntStream.rangeClosed(1, 7)
                .mapToObj(weekBeforeToday::plusMonths)
                .map(LocalDate::getMonth)
                .map(Objects::toString)
                .collect(Collectors.toList());
    }
}