package com.metrarty.LogiWEB.repository;

import com.metrarty.LogiWEB.repository.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
