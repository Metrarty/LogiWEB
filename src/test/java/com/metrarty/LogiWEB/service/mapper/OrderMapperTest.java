package com.metrarty.LogiWEB.service.mapper;

import com.metrarty.LogiWEB.boundary.model.CargoDto;
import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.boundary.model.OrderDto;
import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.repository.entity.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.time.Instant;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderMapperTest {

    @InjectMocks
    private OrderMapper orderMapper;

    @Mock
    private CargoMapper cargoMapper;

    @Mock
    private CityMapper cityMapper;

    @Mock
    private TruckMapper truckMapper;

    private static final Instant NOW = Instant.now();

    private OrderMapper orderMapperSpy;

    @Before
    public void init() {
        orderMapperSpy = Mockito.spy(orderMapper);
    }

    @Test
    public void testToDto() {
        //prepare
        Cargo cargo = new Cargo();
        City source = new City();
        City destination = new City();
        Truck assignedTruck = new Truck();

        Order order = new Order();
        order.setId(1L);
        order.setCargo(cargo);
        order.setSourceCity(source);
        order.setDestination(destination);
        order.setAssignedTruck(assignedTruck);
        order.setDeliveryWorkingDays(2);
        order.setOrderStatus("CREATED");
        order.setCreatedAt(NOW);
        order.setChangedAt(NOW);
        order.setCompletedAt(NOW);

        OrderDto expected = new OrderDto();
        expected.setId(order.getId());
        expected.setCargo(null);
        expected.setDestination(null);
        expected.setDeliveryWorkingDays(order.getDeliveryWorkingDays());
        expected.setAssignedTruck(null);
        expected.setOrderStatus(OrderStatus.valueOf(order.getOrderStatus()));
        expected.setCreatedAt(order.getCreatedAt());
        expected.setChangedAt(order.getChangedAt());
        expected.setCompletedAt(order.getCompletedAt());

        //run
        OrderDto actual = orderMapperSpy.toDto(order);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
        Mockito.verify(cargoMapper, Mockito.times(1)).toDto(cargo);
        Mockito.verify(cityMapper, times(2)).toDto(source);
        Mockito.verify(cityMapper, times(2)).toDto(destination);
        Mockito.verify(truckMapper, times(1)).toDto(assignedTruck);
        Mockito.verifyNoMoreInteractions(cargoMapper, cityMapper, truckMapper);
    }

    @Test
    public void testToInitialEntity() {
        //prepare
        CargoDto cargoDto = new CargoDto();
        CityDto sourceDto = new CityDto();
        CityDto destinationDto= new CityDto();
        TruckDto assignedTruckDto= new TruckDto();
        Truck assignedTruck = new Truck();

        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setCargo(cargoDto);
        orderDto.setSourceCity(sourceDto);
        orderDto.setDestination(destinationDto);
        orderDto.setAssignedTruck(assignedTruckDto);
        orderDto.setDeliveryWorkingDays(2);

        Order expected = new Order();
        expected.setId(orderDto.getId());
        expected.setCargo(null);
        expected.setSourceCity(null);
        expected.setDestination(null);
        expected.setDeliveryWorkingDays(null);
        expected.setCreatedAt(NOW);
        when(orderMapperSpy.getNow()).thenReturn(NOW);
        expected.setAssignedTruck(assignedTruck);
        when(truckMapper.toEntity(assignedTruckDto)).thenReturn(assignedTruck);
        expected.setDeliveryWorkingDays(orderDto.getDeliveryWorkingDays());

        //run
        Order actual = orderMapperSpy.toEntityWithCreatedAt(orderDto);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
        Mockito.verify(cargoMapper, Mockito.times(1)).toEntity(cargoDto);
        Mockito.verify(cityMapper, times(2)).toEntity(sourceDto);
        Mockito.verify(cityMapper, times(2)).toEntity(destinationDto);
        Mockito.verify(truckMapper, times(1)).toEntity(assignedTruckDto);
        Mockito.verifyNoMoreInteractions(cargoMapper, cityMapper, truckMapper);
    }


    @Test
    public void testToUpdatedEntity() {
        //prepare
        CargoDto cargoDto = new CargoDto();
        CityDto sourceDto = new CityDto();
        CityDto destinationDto= new CityDto();
        TruckDto assignedTruckDto= new TruckDto();

        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setCargo(cargoDto);
        orderDto.setSourceCity(sourceDto);
        orderDto.setDestination(destinationDto);
        orderDto.setDeliveryWorkingDays(2);
        orderDto.setAssignedTruck(assignedTruckDto);

        Order expected = new Order();
        expected.setId(orderDto.getId());
        expected.setCargo(null);
        expected.setSourceCity(null);
        expected.setDestination(null);
        expected.setDeliveryWorkingDays(orderDto.getDeliveryWorkingDays());
        expected.setChangedAt(NOW);
        when(orderMapperSpy.getNow()).thenReturn(NOW);
        expected.setAssignedTruck(null);

        //run
        Order actual = orderMapperSpy.toEntityWithChangedAt(orderDto);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
        Mockito.verify(cargoMapper, Mockito.times(1)).toEntity(cargoDto);
        Mockito.verify(cityMapper, times(2)).toEntity(sourceDto);
        Mockito.verify(cityMapper, times(2)).toEntity(destinationDto);
        Mockito.verify(truckMapper, times(1)).toEntity(assignedTruckDto);
        Mockito.verifyNoMoreInteractions(cargoMapper, cityMapper, truckMapper);
    }
}
