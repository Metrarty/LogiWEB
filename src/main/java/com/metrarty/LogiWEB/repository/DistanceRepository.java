package com.metrarty.LogiWEB.repository;


import com.metrarty.LogiWEB.repository.entity.Distance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DistanceRepository extends JpaRepository<Distance, Long> {
    @Query(nativeQuery = true,
    value = "SELECT distance FROM DISTANCE"
            + " WHERE (CITY1_ID = ?1 AND CITY2_ID = ?2) OR (CITY1_ID = ?2 AND CITY2_ID = ?1)"
            + " LIMIT 1")
    Optional<Long> findDistanceBetweenCities(Long sourceCityId, Long destinationCityId);
}
