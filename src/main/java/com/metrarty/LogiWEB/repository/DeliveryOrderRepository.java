package com.metrarty.LogiWEB.repository;

import com.metrarty.LogiWEB.repository.entity.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Long> {
}
