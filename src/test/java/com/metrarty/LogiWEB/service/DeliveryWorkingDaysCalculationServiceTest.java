package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.repository.entity.Order;
import com.metrarty.LogiWEB.repository.entity.Truck;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryWorkingDaysCalculationServiceTest {

    @InjectMocks
    private DeliveryWorkingDaysCalculationService deliveryWorkingDaysCalculationService;

    @Mock
    private DistanceService distanceService;

    @Test
    public void calculateDeliveryWorkingDaysTest() {
        //prepare
        City orderDestination = new City();
        orderDestination.setId(1L);
        City orderSourceCity = new City();
        City truckLocation = new City();
        Truck assignedTruck = new Truck();
        assignedTruck.setDistancePerDay(1000L);
        assignedTruck.setLocation(truckLocation);
        Order inputOrder = new Order();
        inputOrder.setDestination(orderDestination);
        inputOrder.setSourceCity(orderSourceCity);
        inputOrder.setAssignedTruck(assignedTruck);
        Mockito.when(distanceService.distanceBetweenCities(orderSourceCity, orderDestination)).thenReturn(2500L);
        Mockito.when(distanceService.distanceBetweenCities(orderSourceCity, truckLocation)).thenReturn(3000L);

        Integer expected = 6;

        //run
        Integer actual = deliveryWorkingDaysCalculationService.calculateDeliveryWorkingDays(inputOrder);
        //test
        Assert.assertEquals("Must be equal", expected, actual);
        Mockito.verify(distanceService, Mockito.times(1)).distanceBetweenCities(orderSourceCity, orderDestination);
        Mockito.verify(distanceService, Mockito.times(1)).distanceBetweenCities(orderSourceCity, truckLocation);
        Mockito.verifyNoMoreInteractions(distanceService);
    }
}
