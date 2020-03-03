package com.softserve.maklertaboo.repository.request;

import com.softserve.maklertaboo.entity.request.RequestForFlatVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestForFlatVerificationRepository extends JpaRepository<RequestForFlatVerification,Long> {
}
