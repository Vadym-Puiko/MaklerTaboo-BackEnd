package com.softserve.maklertaboo.repository;

import com.softserve.maklertaboo.entity.Subscription;
import com.softserve.maklertaboo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findAllByUser(User user);
    List<Subscription> findAllByActiveIsTrue();

}
