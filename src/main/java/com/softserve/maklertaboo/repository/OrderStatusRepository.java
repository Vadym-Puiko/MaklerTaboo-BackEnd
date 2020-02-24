package com.softserve.maklertaboo.repository;

import com.softserve.maklertaboo.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus,Long> {
}
