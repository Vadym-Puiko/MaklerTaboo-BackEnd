package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.request.RequestForFlatDto;
import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.request.RequestForFlatBooking;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.exception.exceptions.AccessDeniedException;
import com.softserve.maklertaboo.exception.exceptions.RequestAlreadyExistsException;
import com.softserve.maklertaboo.exception.exceptions.RequestForFlatBookingException;
import com.softserve.maklertaboo.mapping.FlatBookingMapper;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.repository.FlatBookingRepository;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.softserve.maklertaboo.constant.ErrorMessage.*;

@Slf4j
@Service
public class FlatBookingService {

    private final FlatBookingRepository flatBookingRepository;
    private final UserMapper userMapper;
    private final FlatService flatService;
    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;
    private final FlatBookingMapper bookingMapper;

    @Autowired
    public FlatBookingService(FlatBookingRepository flatBookingRepository,
                              UserMapper userMapper,
                              FlatService flatService,
                              UserService userService,
                              JWTTokenProvider jwtTokenProvider,
                              FlatBookingMapper bookingMapper) {
        this.flatBookingRepository = flatBookingRepository;
        this.userMapper = userMapper;
        this.flatService = flatService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.bookingMapper = bookingMapper;
    }

    public void createRequestForFlatBooking(Long id) {

        User user = jwtTokenProvider.getCurrentUser();
        UserRole userRole = user.getRole();

        if (userRole.getStatus().equals("ROLE_USER")) {
            throw new AccessDeniedException(ACCESS_DENIED_TO_BOOK_APARTMENT);
        }

        Flat flat = flatService.getById(id);

        RequestForFlatBooking requestForFlatBooking = flatBookingRepository
                .findRequestForFlatBookingByAuthor_IdAndFlat_Id(user.getId(), flat.getId())
                .orElse(null);

        if (requestForFlatBooking == null) {
            RequestForFlatBooking requestForFlatBooking1 = new RequestForFlatBooking();
            requestForFlatBooking1.setFlat(flat);
            requestForFlatBooking1.setAuthor(user);
            flatBookingRepository.save(requestForFlatBooking1);
        } else {
            throw new RequestAlreadyExistsException(REQUEST_FOR_FLAT_BOOKING_ALREADY_EXISTS);
        }
    }

    public Page<RequestForFlatDto> getLandlordRequests(
            Integer page, Integer size, RequestForVerificationStatus status) {

        User user = jwtTokenProvider.getCurrentUser();

        Pageable pageable = PageRequest.of(page, size);
        return flatBookingRepository.findAllByFlat_Owner_IdAndStatus(pageable, user.getId(), status)
                .map(bookingMapper::convertToDto);
    }

    public List<RequestForFlatDto> getRenterRequests() {

        User user = jwtTokenProvider.getCurrentUser();

        List<RequestForFlatBooking> requestForFlatBookings = flatBookingRepository
                .findAllByAuthor_Id(user.getId())
                .orElse(null);

        if (requestForFlatBookings == null) {
            throw new RequestForFlatBookingException(REQUEST_FOR_FLAT_BOOKING_NOT_FOUND);
        }

        return requestForFlatBookings
                .stream()
                .map(bookingMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public void approveFlatRequest(Long id) {
        RequestForFlatBooking requestForFlatBooking = getRequestForFlatBookingById(id);
        requestForFlatBooking.setStatus(RequestForVerificationStatus.APPROVED);
        requestForFlatBooking.setVerificationDate(new Date());

        Flat flat = flatService.getById(requestForFlatBooking.getFlat().getId());
        flat.setIsActive(false);
        flat.setIsBooked(true);
        flatService.saveFlat(flat);

        flatBookingRepository.save(requestForFlatBooking);
    }

    public void declineFlatRequest(Long id) {
        RequestForFlatBooking requestForFlatBooking = getRequestForFlatBookingById(id);
        requestForFlatBooking.setStatus(RequestForVerificationStatus.DECLINED);
        requestForFlatBooking.setVerificationDate(new Date());

        Flat flat = flatService.getById(requestForFlatBooking.getFlat().getId());
        flat.setIsActive(true);
        flat.setIsBooked(false);
        flatService.saveFlat(flat);

        flatBookingRepository.save(requestForFlatBooking);
    }

    public void reviewFlatRequest(Long id) {
        RequestForFlatBooking requestForFlatBooking = getRequestForFlatBookingById(id);
        requestForFlatBooking.setStatus(RequestForVerificationStatus.VIEWED);
        flatBookingRepository.save(requestForFlatBooking);
    }

    public Long getCountOfNewRequests(RequestForVerificationStatus status) {
        return flatBookingRepository.countAllByStatus(status);
    }

    private RequestForFlatBooking getRequestForFlatBookingById(Long id) {
        RequestForFlatBooking requestForFlatBooking = flatBookingRepository
                .findRequestForFlatBookingById(id)
                .orElseThrow(() -> new RequestForFlatBookingException(
                        REQUEST_FOR_FLAT_BOOKING_NOT_FOUND));

        return requestForFlatBooking;
    }
}
