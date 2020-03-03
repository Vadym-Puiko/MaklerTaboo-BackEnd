package com.softserve.maklertaboo.repository.request;

import com.softserve.maklertaboo.entity.request.RequestForVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestForVerificationRepository extends JpaRepository<RequestForVerification, Long> {
}
