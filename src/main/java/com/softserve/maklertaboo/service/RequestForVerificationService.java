package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.request.RequestForFlatVerification;
import com.softserve.maklertaboo.entity.request.RequestForUserVerification;
import com.softserve.maklertaboo.repository.request.RequestForVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestForVerificationService {
    private final RequestForVerificationRepository requestForVerificationRepository;
    private final RequestForUserVerification requestForUserVerification;
    private final RequestForFlatVerification requestForFlatVerification;

    @Autowired
    public RequestForVerificationService(RequestForVerificationRepository requestForVerificationRepository,
                                         RequestForUserVerification requestForUserVerification,
                                         RequestForFlatVerification requestForFlatVerification) {
        this.requestForVerificationRepository = requestForVerificationRepository;
        this.requestForUserVerification = requestForUserVerification;
        this.requestForFlatVerification = requestForFlatVerification;
    }
}
