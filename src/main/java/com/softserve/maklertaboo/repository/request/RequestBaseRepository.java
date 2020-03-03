package com.softserve.maklertaboo.repository.request;

import com.softserve.maklertaboo.entity.request.RequestForVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RequestBaseRepository<T extends RequestForVerification> extends JpaRepository<T, Long> {
}
