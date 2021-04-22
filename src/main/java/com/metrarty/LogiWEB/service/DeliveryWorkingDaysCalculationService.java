package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.repository.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class DeliveryWorkingDaysCalculationService {

    private final DistanceService distanceService;

    public Integer calculateDeliveryWorkingDays(Order order) {
        City orderDestination = order.getDestination();
        City orderSourceCity = order.getSourceCity();
        City truckLocation = order.getAssignedTruck().getLocation();
        Long distancePerDay = order.getAssignedTruck().getDistancePerDay();
        Long distanceBetweenSourceAndDestination = distanceService.distanceBetweenCities(orderSourceCity, orderDestination);
        Long distanceFromTruckToSourceCity = distanceService.distanceBetweenCities(orderSourceCity, truckLocation);

        Long distanceTotal = distanceBetweenSourceAndDestination+distanceFromTruckToSourceCity;
        Long result = distanceTotal / distancePerDay;

        return addOneDayIfDivisionModNotNull(distanceTotal, distancePerDay, result).intValue();
    }

    private Long addOneDayIfDivisionModNotNull (Long distanceTotal, Long distancePerDay, Long result) {
        long mod = distanceTotal % distancePerDay;
        if (mod != 0) {
            result = result +1;
        }
        return result;
    }
}
