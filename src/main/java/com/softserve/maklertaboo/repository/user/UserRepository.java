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
import java.util.Optional;

/**
 * Provides an interface to manage {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find {@link User} by page.
     *
     * @param pageable pageable configuration.
     * @return {@link Page} without User with ROLE_ADMIN
     * @author Vadym Puiko
     */
    @Query("from User user where not user.role='ROLE_ADMIN'")
    Page<User> findAll(Pageable pageable);

    /**
     * Find {@link User} by username.
     *
     * @param username user username.
     * @return {@link User}
     * @author Vadym Puiko
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Find {@link User} by email.
     *
     * @param email user email.
     * @return user {@link User}
     * @author Vadym Puiko
     */
    Optional<User> findUserByEmail(String email);

    /**
     * Find {@link User} by phone number.
     *
     * @param phoneNumber user phone number.
     * @return user {@link User}
     * @author Vadym Puiko
     */
    Optional<User> findUserByPhoneNumber(String phoneNumber);

    /**
     * The method that check if user with this email already exist
     *
     * @param email user email.
     * @return boolean value
     * @author Vadym Puiko
     */
    boolean existsUserByEmail(String email);

    /**
     * The method that check if user with this username already exist
     *
     * @param username user username.
     * @return boolean value
     * @author Vadym Puiko
     */
    boolean existsUserByUsername(String username);

    /**
     * The method that check if user with this phone already exist
     *
     * @param phone user phone.
     * @return boolean value
     * @author Vadym Puiko
     */
    boolean existsUserByPhoneNumber(String phone);

    /**
     * Find {@link User} by page.
     *
     * @param pageable pageable configuration.
     * @param username string.
     * @return {@link Page} without User with ROLE_ADMIN
     * @author Vadym Puiko
     */
    @Query("from User user where not user.role='ROLE_ADMIN' and user.username like :username")
    Page<User> findAllByUsernameLike(Pageable pageable, String username);

    /**
     * Find {@link User} by page.
     *
     * @param pageable pageable configuration.
     * @param email    string.
     * @return {@link Page} without User with ROLE_ADMIN
     * @author Vadym Puiko
     */
    @Query("from User user where not user.role='ROLE_ADMIN' and user.email like :email")
    Page<User> findAllByEmailLike(Pageable pageable, String email);

    /**
     * Find {@link User} by page.
     *
     * @param pageable pageable configuration.
     * @param phone    string.
     * @return {@link Page} without User with ROLE_ADMIN
     * @author Vadym Puiko
     */
    @Query("from User user where not user.role='ROLE_ADMIN' and user.phoneNumber like :phone")
    Page<User> findAllByPhoneNumberLike(Pageable pageable, String phone);

    /**
     * The method counts all users by user role.
     *
     * @param userRole user userRole.
     * @return amount of user with given {@link UserRole}.
     * @author Andriy Pyzh
     */
    long countAllByRole(UserRole userRole);

    /**
     * The method counts all users by user registration date between.
     *
     * @param start user date.
     * @param end   user date.
     * @return amount of user with given {@link Date}.
     * @author Andriy Pyzh
     */
    long countAllByRegistrationDateBetween(Date start, Date end);

    /**
     * The method counts all users by user registration date before.
     *
     * @param start user date.
     * @return amount of user with given {@link Date}.
     * @author Andriy Pyzh
     */
    long countAllByRegistrationDateBefore(Date start);

    /**
     * Updates refresh token for a given user.
     *
     * @param secret - new refresh token key
     * @param id     - user's id
     * @author Mike Ostapiuk
     */
    @Modifying
    @Query("update User u set u.refreshKey= ?1 where u.id = ?2")
    void updateRefreshKey(String secret, Long id);

    List<User> findAllByRole(UserRole role);

    @Query("SELECT COUNT(u) FROM User u" +
            " WHERE u.role = :role AND u.registrationDate < :date AND u.status='ACTIVE'")
    Long countAllActiveByRoleAndRegistrationDateBefore(UserRole role, Date date);


}
