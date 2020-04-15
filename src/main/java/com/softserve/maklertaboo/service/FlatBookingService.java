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
import com.softserve.maklertaboo.repository.FlatBookingRepository;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static com.softserve.maklertaboo.utils.DateUtils.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.softserve.maklertaboo.constant.ErrorMessage.*;


/**
 * Service implementation for {@link RequestForFlatBooking} entity.
 *
 * @author Roman Blavatskyi
 */
@Slf4j
@Service
public class FlatBookingService {

    private final FlatBookingRepository flatBookingRepository;
    private final FlatService flatService;
    private final JWTTokenProvider jwtTokenProvider;
    private final FlatBookingMapper bookingMapper;

    /**
     * Constructor with parameters of {@link FlatBookingService}.
     *
     * @author Roman Blavatskyi
     */
    @Autowired
    public FlatBookingService(FlatBookingRepository flatBookingRepository,
                              FlatService flatService,
                              JWTTokenProvider jwtTokenProvider,
                              FlatBookingMapper bookingMapper) {
        this.flatBookingRepository = flatBookingRepository;
        this.flatService = flatService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.bookingMapper = bookingMapper;
    }

    /**
     * Method that creates new entity of {@link RequestForFlatBooking}.
     *
     * @param id of {@link Flat}
     * @author Roman Blavatskyi
     */
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

    /**
     * Method that finds all {@link RequestForFlatBooking} for Landlord.
     *
     * @param page   Integer
     * @param size   Integer
     * @param status {@link RequestForVerificationStatus}
     * @return {@link Page} of {@link RequestForFlatDto}
     * @author Roman Blavatskyi
     */
    public Page<RequestForFlatDto> getLandlordRequests(
            Integer page, Integer size, RequestForVerificationStatus status) {

        User user = jwtTokenProvider.getCurrentUser();

        Pageable pageable = PageRequest.of(page, size);
        return flatBookingRepository.findAllByFlat_Owner_IdAndStatus(pageable, user.getId(), status)
                .map(bookingMapper::convertToDto);
    }

    /**
     * Method that finds all {@link RequestForFlatBooking} for Renter.
     *
     * @return {@link List<RequestForFlatDto>}
     * @author Roman Blavatskyi
     */
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
                .filter(request -> request.getStatus() != (RequestForVerificationStatus.DECLINED))
                .map(bookingMapper::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Method that approves {@link RequestForFlatBooking} of Renter.
     *
     * @param id of {@link RequestForFlatBooking}
     * @author Roman Blavatskyi
     */
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

    /**
     * Method that declines {@link RequestForFlatBooking} of Renter.
     *
     * @param id of {@link RequestForFlatBooking}
     * @author Roman Blavatskyi
     */
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

    /**
     * Method that allows review {@link RequestForFlatBooking} of Renter
     * and change its status.
     *
     * @param id of {@link RequestForFlatBooking}
     * @author Roman Blavatskyi
     */
    public void reviewFlatRequest(Long id) {
        RequestForFlatBooking requestForFlatBooking = getRequestForFlatBookingById(id);
        requestForFlatBooking.setStatus(RequestForVerificationStatus.VIEWED);
        flatBookingRepository.save(requestForFlatBooking);
    }

    /**
     * Method that finds amount of new {@link RequestForFlatBooking}.
     *
     * @param status of {@link RequestForVerificationStatus}
     * @return amount of {@link RequestForVerificationStatus}
     * @author Roman Blavatskyi
     */
    public Long getCountOfNewRequests(RequestForVerificationStatus status) {
        return flatBookingRepository.countAllByStatus(status);
    }

    /**
     * Method that finds {@link RequestForFlatBooking} by id.
     *
     * @param id of {@link RequestForFlatBooking}
     * @return {@link RequestForFlatBooking}
     * @author Roman Blavatskyi
     */
    private RequestForFlatBooking getRequestForFlatBookingById(Long id) {
        RequestForFlatBooking requestForFlatBooking = flatBookingRepository
                .findRequestForFlatBookingById(id)
                .orElseThrow(() -> new RequestForFlatBookingException(
                        REQUEST_FOR_FLAT_BOOKING_NOT_FOUND));

        return requestForFlatBooking;
    }

    public Long countApprovedRequestsBetween(LocalDate start, LocalDate end) {
        return flatBookingRepository.countAllApprovedRequestsByVerificationDateBetween(asDate(start),asDate(end));
    }

    public Long countApprovedRequestsOfLandlord(Long id) {
        return flatBookingRepository.findAllApproved().stream()
                .filter(request -> request.getFlat().getOwner().getId().equals(id)).count();
    }
}
