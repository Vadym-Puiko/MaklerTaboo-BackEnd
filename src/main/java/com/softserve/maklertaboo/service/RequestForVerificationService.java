package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.constant.ErrorMessage;
import com.softserve.maklertaboo.dto.request.RequestForUserDto;
import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import com.softserve.maklertaboo.entity.enums.RequestForVerificationType;
import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.request.RequestForFlatBooking;
import com.softserve.maklertaboo.entity.request.RequestForFlatVerification;
import com.softserve.maklertaboo.entity.request.RequestForUserVerification;
import com.softserve.maklertaboo.entity.request.RequestForVerification;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.exception.exceptions.AccessDeniedException;
import com.softserve.maklertaboo.exception.exceptions.RequestAlreadyExistsException;
import com.softserve.maklertaboo.exception.exceptions.RequestNotFoundException;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.mapping.request.RequestForFlatMapper;
import com.softserve.maklertaboo.mapping.request.RequestForUserMapper;
import com.softserve.maklertaboo.repository.request.RequestBaseRepository;
import com.softserve.maklertaboo.repository.request.RequestForFlatBookingRepository;
import com.softserve.maklertaboo.repository.request.RequestForFlatVerificationRepository;
import com.softserve.maklertaboo.repository.request.RequestForUserVerificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RequestForVerificationService {
    private final RequestForFlatVerificationRepository requestFlatRepository;
    private final RequestForUserVerificationRepository requestUserRepository;
    private final RequestForFlatBookingRepository requestForFlatBookingRepository;
    private final FlatService flatService;
    private final UserService userService;
    private final RequestForUserMapper requestForUserMapper;
    private final RequestForFlatMapper requestForFlatMapper;
    private final UserMapper userMapper;

    @Autowired
    public RequestForVerificationService(RequestForFlatVerificationRepository requestForFlatVerificationRepository,
                                         RequestForUserVerificationRepository requestForUserVerificationRepository,
                                         RequestForFlatBookingRepository requestForFlatBookingRepository,
                                         FlatService flatService,
                                         UserService userService,
                                         RequestForUserMapper requestForUserMapper,
                                         RequestForFlatMapper requestForFlatMapper,
                                         UserMapper userMapper) {

        this.requestFlatRepository = requestForFlatVerificationRepository;
        this.requestUserRepository = requestForUserVerificationRepository;
        this.requestForFlatBookingRepository = requestForFlatBookingRepository;
        this.flatService = flatService;
        this.userService = userService;
        this.requestForUserMapper = requestForUserMapper;
        this.requestForFlatMapper = requestForFlatMapper;
        this.userMapper = userMapper;
    }

    public void createRequestForUserVerification(RequestForUserDto requestForUserDto, RequestForVerificationType type) {
        RequestForUserVerification requestForUserVerification = requestForUserMapper.convertToEntity(requestForUserDto);
        requestForUserVerification.setType(type);
        requestUserRepository.save(requestForUserVerification);
    }

    public void createRequestForFlatBooking(Long id, String email) {

        User user = userMapper.convertToEntity(userService.findByEmail(email));
        UserRole userRole = user.getRole();

        if (userRole.getStatus().equals("ROLE_USER")) {
            throw new AccessDeniedException(ErrorMessage
                    .ACCESS_DENIED_TO_BOOK_APARTMENT);
        }

        Flat flat = flatService.getById(id);

        RequestForFlatBooking requestForFlatBooking = requestForFlatBookingRepository
                .findRequestForFlatBookingByAuthor_IdAndFlat_Id(user.getId(), flat.getId());

        if (requestForFlatBooking == null) {
            RequestForFlatBooking requestForFlatBooking1 = new RequestForFlatBooking();
            requestForFlatBooking1.setFlat(flat);
            requestForFlatBooking1.setAuthor(user);
            requestForFlatBookingRepository.save(requestForFlatBooking1);
        } else {
            throw new RequestAlreadyExistsException(ErrorMessage
                    .REQUEST_FOR_FLAT_BOOKING_ALREADY_EXISTS);
        }

    }



    public List<RequestForFlatVerification> getAllRequestsForFlatVerification() {
        return requestFlatRepository.findAll();
    }

    public List<RequestForUserVerification> getAllRequestsForRenterVerification() {
        return requestUserRepository.findAll().stream()
                .filter(request -> request.getType() == RequestForVerificationType.RENTER)
                .collect(Collectors.toList());
    }

    public List<RequestForUserVerification> getAllRequestsForLandlordVerification() {
        return requestUserRepository.findAll().stream()
                .filter(request -> request.getType() == RequestForVerificationType.LANDLORD)
                .collect(Collectors.toList());
    }

    public List<RequestForUserVerification> getAllRequestsForModeratorVerification() {
        return requestUserRepository.findAll().stream()
                .filter(request -> request.getType() == RequestForVerificationType.MODERATOR)
                .collect(Collectors.toList());
    }

    public void approveFlatRequest(Long id) {
        RequestForFlatVerification requestForFlatVerification = getRequestsForFlatVerificationById(id);
        requestForFlatVerification.setStatus(RequestForVerificationStatus.APPROVED);
        requestForFlatVerification.setVerificationDate(new Date());
        flatService.activate(requestForFlatVerification.getFlat().getId());
        requestFlatRepository.save(requestForFlatVerification);
    }

    public void bannedFlatRequest(Long id) {
        RequestForFlatVerification requestForFlatVerification = getRequestsForFlatVerificationById(id);
        requestForFlatVerification.setStatus(RequestForVerificationStatus.DEACTIVATED);
        requestForFlatVerification.setVerificationDate(new Date());
        flatService.activate(requestForFlatVerification.getFlat().getId());
        requestFlatRepository.save(requestForFlatVerification);
    }

    public void approveUserRequest(Long id) {
        RequestForUserVerification requestForUserVerification = getRequestsForUserVerificationById(id);
        requestForUserVerification.setStatus(RequestForVerificationStatus.APPROVED);
        requestForUserVerification.setVerificationDate(new Date());

        switch (requestForUserVerification.getType()) {
            case RENTER:
                userService.updateRole(requestForUserVerification.getAuthor().getId(), UserRole.ROLE_RENTER);
                break;
            case LANDLORD:
                userService.updateRole(requestForUserVerification.getAuthor().getId(), UserRole.ROLE_LANDLORD);
                break;
            case MODERATOR:
                userService.updateRole(requestForUserVerification.getAuthor().getId(), UserRole.ROLE_MODERATOR);
                break;
        }
        requestUserRepository.save(requestForUserVerification);
    }

    public void declineFlatRequest(Long id) {
        declineRequest(requestFlatRepository, id);
    }

    public void declineUserRequest(Long id) {
        declineRequest(requestUserRepository, id);
    }

    public RequestForUserVerification getRequestsForUserVerificationById(Long id) {
        return getRequestsForVerificationById(requestUserRepository, id);
    }

    public RequestForFlatVerification getRequestsForFlatVerificationById(Long id) {
        return getRequestsForVerificationById(requestFlatRepository, id);
    }

    private <T extends RequestForVerification> T getRequestsForVerificationById(RequestBaseRepository<T> requestBaseRepository, Long id) {
        Optional<T> request = requestBaseRepository.findById(id);
        if (request.isPresent()) {
            return request.get();
        } else {
            throw new RequestNotFoundException(ErrorMessage.REQUEST_NOT_FOUND + id);
        }
    }

    private <T extends RequestForVerification> void declineRequest(RequestBaseRepository<T> requestBaseRepository, Long id) {
        T requestForVerification = getRequestsForVerificationById(requestBaseRepository, id);
        requestForVerification.setStatus(RequestForVerificationStatus.DECLINED);
        requestForVerification.setVerificationDate(new Date());
        requestBaseRepository.save(requestForVerification);
    }

    public Page<RequestForFlatVerification> getAllAndBannedFlats(Integer page, Integer size,
                                                                 RequestForVerificationStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("creationDate")));
        if (status == RequestForVerificationStatus.DEACTIVATED) {
            return requestFlatRepository.findAllByStatus(pageable, status);
        } else {
            return requestFlatRepository.findAll(pageable);
        }
    }


    public Page<RequestForFlatVerification> getRequestsForFlatVerification(Integer page, Integer size,
                                                                           RequestForVerificationStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("creationDate")));
        return requestFlatRepository.findAllByStatus(pageable, status);
    }

    public Page<RequestForUserVerification> getRequestsForRenterVerification(Integer page, Integer size,
                                                                             RequestForVerificationStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("creationDate")));
        return requestUserRepository.findAllByStatusAndType(pageable, status, RequestForVerificationType.RENTER);
    }

    public Page<RequestForUserVerification> getRequestsForLandlordVerification(Integer page, Integer size,
                                                                               RequestForVerificationStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("creationDate")));
        return requestUserRepository.findAllByStatusAndType(pageable, status, RequestForVerificationType.LANDLORD);
    }

    public Page<RequestForUserVerification> getRequestsForModeratorVerification(Integer page, Integer size,
                                                                                RequestForVerificationStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("creationDate")));
        return requestUserRepository.findAllByStatusAndType(pageable, status, RequestForVerificationType.MODERATOR);
    }

    public void reviewFlatRequest(Long id) {
        RequestForFlatVerification request = getRequestsForFlatVerificationById(id);
        request.setStatus(RequestForVerificationStatus.VIEWED);
        requestFlatRepository.save(request);
    }

    public void reviewUserRequest(Long id) {
        RequestForUserVerification request = getRequestsForUserVerificationById(id);
        request.setStatus(RequestForVerificationStatus.VIEWED);
        requestUserRepository.save(request);
    }


    public Long getCountOfNewRequests(RequestForVerificationStatus status) {
        return requestFlatRepository.countAllByStatus(status) +
                requestUserRepository.countAllByStatus(status);
    }

    public void createFlatRequest(Flat flat) {
        RequestForFlatVerification request = new RequestForFlatVerification();
        request.setFlat(flat);
        request.setAuthor(flat.getOwner());
        requestFlatRepository.save(request);
    }

    public void createRenterRequest(User user) {
        RequestForUserVerification request = new RequestForUserVerification();
        request.setAuthor(user);
        request.setType(RequestForVerificationType.RENTER);
        requestUserRepository.save(request);
    }

    public void createLandlordRequest(User user) {
        RequestForUserVerification request = new RequestForUserVerification();
        request.setAuthor(user);
        request.setType(RequestForVerificationType.LANDLORD);
        requestUserRepository.save(request);
    }
}
