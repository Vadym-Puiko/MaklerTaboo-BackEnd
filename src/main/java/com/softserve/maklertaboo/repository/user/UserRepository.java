package com.softserve.maklertaboo.repository.user;

import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    Page<User> findAllByUsernameLike(Pageable pageable, String username);

    Page<User> findAllByEmailLike(Pageable pageable, String email);

    Page<User> findAllByPhoneNumberLike(Pageable pageable, String phone);

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

    @Modifying
    @Query("UPDATE User SET password = :password WHERE id = :id")
    void updatePassword(@Param("password") String password, @Param("id") Long id);
}
