package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import com.softserve.maklertaboo.entity.request.RequestForFlatVerification;
import com.softserve.maklertaboo.entity.request.RequestForLandlordVerification;
import com.softserve.maklertaboo.entity.request.RequestForVerification;
import com.softserve.maklertaboo.exception.DataNotFoundException;
import com.softserve.maklertaboo.repository.request.RequestBaseRepository;
import com.softserve.maklertaboo.repository.request.RequestForFlatVerificationRepository;
import com.softserve.maklertaboo.repository.request.RequestForLandlordVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RequestForVerificationService {
    private final RequestForFlatVerificationRepository requestFlatRepository;
    private final RequestForLandlordVerificationRepository requestLandlordRepository;
    private final FlatService flatService;
    private final UserService userService;

    @Autowired
    public RequestForVerificationService(RequestForFlatVerificationRepository requestForFlatVerificationRepository,
                                         RequestForLandlordVerificationRepository requestForLandlordVerificationRepository,
                                         FlatService flatService, UserService userService) {

        this.requestFlatRepository = requestForFlatVerificationRepository;
        this.requestLandlordRepository = requestForLandlordVerificationRepository;
        this.flatService = flatService;
        this.userService = userService;
    }

    public List<RequestForFlatVerification> getAllRequestsForFlatVerification() {
        return requestFlatRepository.findAll();
    }

    public List<RequestForLandlordVerification> getAllRequestsForLandlordVerification() {
        return requestLandlordRepository.findAll();
    }

    public void approveFlatRequest(Long id) {
        RequestForFlatVerification requestForFlatVerification = getRequestsForFlatVerificationById(id);
        requestForFlatVerification.setStatus(RequestForVerificationStatus.APPROVED);
        requestForFlatVerification.setVerificationDate(new Date());
        flatService.setActiveTrue(requestForFlatVerification.getFlat().getId());
    }

    public void approveLandlordRequest(Long id) {
        RequestForLandlordVerification requestForLandlordVerification = getRequestsForLandlordVerificationById(id);
        requestForLandlordVerification.setStatus(RequestForVerificationStatus.APPROVED);
        requestForLandlordVerification.setVerificationDate(new Date());
        userService.makeLandlord(requestForLandlordVerification.getUser().getId());
    }

    public RequestForLandlordVerification getRequestsForLandlordVerificationById(Long id) {
        return getRequestsForVerificationById(requestLandlordRepository, id);
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


}
