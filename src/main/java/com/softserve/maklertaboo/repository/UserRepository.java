package com.softserve.maklertaboo.repository;

import com.softserve.maklertaboo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getByUsername(String username);
}
