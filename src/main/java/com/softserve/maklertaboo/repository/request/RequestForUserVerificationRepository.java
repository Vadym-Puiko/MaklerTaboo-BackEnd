package com.softserve.maklertaboo.repository.request;

import com.softserve.maklertaboo.entity.enums.RequestForVerificationType;
import com.softserve.maklertaboo.entity.request.RequestForUserVerification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RequestForUserVerificationRepository extends RequestBaseRepository<RequestForUserVerification> {
    @Query("SELECT COUNT(f)" +
            " FROM RequestForUserVerification" +
            " f WHERE f.status='APPROVED' AND f.type=:type AND f.verificationDate <:start")
    long countAllVerificationDateLessAndStatusIsApproved(Date start, RequestForVerificationType type);
}


