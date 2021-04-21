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
        Long distanceBetweenDestinationAndSource = distanceService.distanceBetweenCities(orderDestination, orderSourceCity);
        Long distanceFromTruckToSourceCity = distanceService.distanceBetweenCities(orderDestination, truckLocation);
        return Math.toIntExact(((distanceFromTruckToSourceCity + distanceBetweenDestinationAndSource) / distancePerDay))+1;
    }

    // TODO metrarty 21.04.2021: it produces a wrong value if for example - > ( 6 / 3 ) + 1 = 4 instead of 3
    //due to it this logic must be updated
}
