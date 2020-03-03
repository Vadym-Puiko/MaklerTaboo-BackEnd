package com.softserve.maklertaboo.repository.request;

import com.softserve.maklertaboo.entity.request.RequestForFlatVerification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestForFlatVerificationRepository extends RequestBaseRepository<RequestForFlatVerification> {
    Page<RequestForFlatVerification> findAllBy(Pageable pageable);

}
