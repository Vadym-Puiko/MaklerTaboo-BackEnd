package com.softserve.maklertaboo.repository.request;

import com.softserve.maklertaboo.entity.request.RequestForUserVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestForUserVerificationRepository extends JpaRepository<RequestForUserVerification,Long> {
}
