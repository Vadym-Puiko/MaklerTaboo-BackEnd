package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.request.RequestForFlatVerification;
import com.softserve.maklertaboo.entity.request.RequestForUserVerification;
import com.softserve.maklertaboo.entity.request.RequestForVerification;
import com.softserve.maklertaboo.repository.request.RequestBaseRepository;
import com.softserve.maklertaboo.repository.request.RequestForFlatVerificationRepository;
import com.softserve.maklertaboo.repository.request.RequestForUserVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<RequestForUserVerification> getAllRequestsForUserVerification() {
        return requestUserRepository.findAll();
    }

    public RequestForUserVerification getRequestsForUserVerificationById(Long id)
            throws Exception {
        return getRequestsForVerificationById(requestUserRepository, id);
    }

    public RequestForFlatVerification getRequestsForFlatVerificationById(Long id)
            throws Exception {
        return getRequestsForVerificationById(requestFlatRepository, id);
    }

    private <T extends RequestForVerification> T getRequestsForVerificationById(RequestBaseRepository<T> requestBaseRepository, Long id)
            throws Exception {
        Optional<T> request = requestBaseRepository.findById(id);
        if (request.isPresent()) {
            return request.get();
        } else {
            throw new Exception();
        }
    }


}
