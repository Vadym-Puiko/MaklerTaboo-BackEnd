package com.softserve.maklertaboo.repository;

import com.softserve.maklertaboo.entity.VerifyEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerifyEmailRepository extends JpaRepository<VerifyEmail, Long> {
    Optional<VerifyEmail> findByToken(String verificationToken);
    Optional<VerifyEmail> findByUserId(Long userId);
}
