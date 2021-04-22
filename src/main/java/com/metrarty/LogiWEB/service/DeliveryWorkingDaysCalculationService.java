package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.repository.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class DeliveryWorkingDaysCalculationService {

    private final DistanceService distanceService;

    public Integer calculateDeliveryWorkingDays(Order order) {
        City orderDestination = order.getDestination();
        City orderSourceCity = order.getSourceCity();
        City truckLocation = order.getAssignedTruck().getLocation();
        Double distancePerDay = Double.valueOf(order.getAssignedTruck().getDistancePerDay());
        Double distanceBetweenDestinationAndSource = Double.valueOf(distanceService.distanceBetweenCities(orderDestination, orderSourceCity));
        Double distanceFromTruckToSourceCity = Double.valueOf(distanceService.distanceBetweenCities(orderDestination, truckLocation));
        if (distanceFromTruckToSourceCity == 0) {
            distanceFromTruckToSourceCity = distanceBetweenDestinationAndSource;
        }
        BigDecimal result = new BigDecimal((distanceFromTruckToSourceCity + distanceBetweenDestinationAndSource) / distancePerDay).setScale(0, RoundingMode.HALF_UP);
        return result.intValue();
    }
}
