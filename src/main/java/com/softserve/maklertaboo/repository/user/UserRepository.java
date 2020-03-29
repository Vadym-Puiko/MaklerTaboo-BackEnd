package com.softserve.maklertaboo.repository.user;

import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User findUserByPhoneNumber(String phoneNumber);

    boolean existsUserByEmail(String email);

    Page<User> findAll(Pageable pageable);

    long count();

    Long countAllByRole(UserRole userRole);

    User getByUsername(String username);

    long countAllByRegistrationDateBetween(Date start, Date end);

    long countAllByRegistrationDateBefore(Date start);

    @Modifying
    @Query("update User u set u.refreshKey= ?1 where u.id = ?2")
    void updateRefreshKey(String secret, Long id);

    List<User> findAllByRole(UserRole role);

}
