package com.softserve.maklertaboo.repository;

import com.softserve.maklertaboo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
