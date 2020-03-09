package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import com.softserve.maklertaboo.entity.enums.RequestForVerificationType;
import com.softserve.maklertaboo.entity.request.RequestForFlatVerification;
import com.softserve.maklertaboo.entity.request.RequestForUserVerification;
import com.softserve.maklertaboo.entity.request.RequestForVerification;
import com.softserve.maklertaboo.exception.DataNotFoundException;
import com.softserve.maklertaboo.repository.request.RequestBaseRepository;
import com.softserve.maklertaboo.repository.request.RequestForFlatVerificationRepository;
import com.softserve.maklertaboo.repository.request.RequestForUserVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestForVerificationService {
    private final RequestForFlatVerificationRepository requestFlatRepository;
    private final RequestForUserVerificationRepository requestUserRepository;
    private final FlatService flatService;
    private final UserService userService;

    @Autowired
    public RequestForVerificationService(RequestForFlatVerificationRepository requestForFlatVerificationRepository,
                                         RequestForUserVerificationRepository requestForUserVerificationRepository,
                                         FlatService flatService, UserService userService) {

        this.requestFlatRepository = requestForFlatVerificationRepository;
        this.requestUserRepository = requestForUserVerificationRepository;
        this.flatService = flatService;
        this.userService = userService;
    }

    public List<RequestForFlatVerification> getAllRequestsForFlatVerification() {
        return requestFlatRepository.findAll();
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

    public void approveUserRequest(Long id) {
        RequestForUserVerification requestForUserVerification = getRequestsForUserVerificationById(id);
        requestForUserVerification.setStatus(RequestForVerificationStatus.APPROVED);
        requestForUserVerification.setVerificationDate(new Date());

        switch (requestForUserVerification.getType()) {
            case LANDLORD:
                userService.makeLandlord(requestForUserVerification.getAuthor().getId());
                break;
            case MODERATOR:
                userService.makeModerator(requestForUserVerification.getAuthor().getId());
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
            throw new DataNotFoundException();
        }
    }

    private <T extends RequestForVerification> void declineRequest(RequestBaseRepository<T> requestBaseRepository, Long id) {
        T requestForVerification = getRequestsForVerificationById(requestBaseRepository, id);
        requestForVerification.setStatus(RequestForVerificationStatus.DECLINED);
        requestForVerification.setVerificationDate(new Date());
        requestBaseRepository.save(requestForVerification);
    }
}
