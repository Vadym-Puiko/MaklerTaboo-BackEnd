package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.request.RequestForFlatVerification;
import com.softserve.maklertaboo.repository.request.RequestForFlatVerificationRepository;
import com.softserve.maklertaboo.repository.request.RequestForUserVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestForVerificationService {
    private final RequestForFlatVerificationRepository requestFlatRepository;
    private final RequestForUserVerificationRepository requestUserRepository;

    @Autowired
    public RequestForVerificationService(RequestForFlatVerificationRepository requestForFlatVerificationRepository,
                                         RequestForUserVerificationRepository requestForUserVerificationRepository) {

        this.requestFlatRepository = requestForFlatVerificationRepository;
        this.requestUserRepository = requestForUserVerificationRepository;
    }

    public List<RequestForFlatVerification> getAllRequestsForFlatVerification() {
        return requestFlatRepository.findAll();
    }


}
