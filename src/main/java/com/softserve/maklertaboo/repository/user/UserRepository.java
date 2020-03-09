package com.softserve.maklertaboo.repository.user;

import com.softserve.maklertaboo.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    boolean existsUserByEmail(String email);

    Page<User> findAll(Pageable pageable);

    User getByUsername(String username);
}
