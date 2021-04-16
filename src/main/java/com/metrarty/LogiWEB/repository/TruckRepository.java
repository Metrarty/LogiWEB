package com.metrarty.LogiWEB.repository;

import com.metrarty.LogiWEB.repository.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TruckRepository extends JpaRepository<Truck, Long> {
    List<Truck> findAllByTruckStatus(String truckStatus);
}
