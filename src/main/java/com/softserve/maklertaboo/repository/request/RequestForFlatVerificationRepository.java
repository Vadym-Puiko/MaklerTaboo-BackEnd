package com.softserve.maklertaboo.repository.request;

import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import com.softserve.maklertaboo.entity.request.RequestForFlatVerification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RequestForFlatVerificationRepository extends RequestBaseRepository<RequestForFlatVerification> {
    @Query("SELECT COUNT(f)" +
            " FROM RequestForFlatVerification f" +
            " WHERE  f.status='APPROVED' AND f.verificationDate between :start AND :end")
    long countAllVerificationDateBetweenAndStatusIsApproved(Date start, Date end);

    @Query("SELECT COUNT(f)" +
            " FROM RequestForFlatVerification f" +
            " WHERE  f.status='APPROVED' AND f.verificationDate < :start")
    long countAllVerificationDateBeforeAndStatusIsApproved(Date start);


    Page<RequestForFlatVerification> findAllByStatus(Pageable pageable, RequestForVerificationStatus status);

}
