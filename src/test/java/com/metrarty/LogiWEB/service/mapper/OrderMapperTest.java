package com.metrarty.LogiWEB.service.mapper;

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


import static org.mockito.Mockito.when;

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
        expected.setCargo(cargoMapper.toDto(order.getCargo()));
        expected.setDestination(cityMapper.toDto(order.getDestination()));
        expected.setDeliveryWorkingDays(order.getDeliveryWorkingDays());
        expected.setAssignedTruck(truckMapper.toDto(order.getAssignedTruck()));
        expected.setOrderStatus(OrderStatus.valueOf(order.getOrderStatus()));
        expected.setCreatedAt(order.getCreatedAt());
        expected.setChangedAt(order.getChangedAt());
        expected.setCompletedAt(order.getCompletedAt());

        //run
        OrderDto actual = orderMapperSpy.toDto(order);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @Test
    public void testToInitialEntity() {
        //prepare
        Cargo cargo = new Cargo();
        City source = new City();
        City destination = new City();
        Truck assignedTruck = new Truck();
        assignedTruck.setId(1L);
        TruckDto assignedTruckDto = new TruckDto();
        assignedTruckDto.setId(1L);

        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setCargo(cargoMapper.toDto(cargo));
        orderDto.setSourceCity(cityMapper.toDto(source));
        orderDto.setDestination(cityMapper.toDto(destination));
        orderDto.setAssignedTruck(assignedTruckDto);
        orderDto.setDeliveryWorkingDays(2);

        Order expected = new Order();
        expected.setId(orderDto.getId());
        expected.setCargo(cargoMapper.toEntity(orderDto.getCargo()));
        expected.setSourceCity(cityMapper.toEntity(orderDto.getSourceCity()));
        expected.setDestination(cityMapper.toEntity(orderDto.getDestination()));
        expected.setDeliveryWorkingDays(orderDto.getDeliveryWorkingDays());
        expected.setCreatedAt(NOW);
        when(orderMapperSpy.getNow()).thenReturn(NOW);
        expected.setAssignedTruck(assignedTruck);
        when(truckMapper.toEntity(assignedTruckDto)).thenReturn(assignedTruck);

        //run
        Order actual = orderMapperSpy.toEntityWithCreatedAt(orderDto);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }


    @Test
    public void testToUpdatedEntity() {
        //prepare
        Cargo cargo = new Cargo();
        City source = new City();
        City destination = new City();
        Truck assignedTruck = new Truck();

        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setCargo(cargoMapper.toDto(cargo));
        orderDto.setSourceCity(cityMapper.toDto(source));
        orderDto.setDestination(cityMapper.toDto(destination));
        orderDto.setDeliveryWorkingDays(2);
        orderDto.setAssignedTruck(truckMapper.toDto(assignedTruck));

        Order expected = new Order();
        expected.setId(orderDto.getId());
        expected.setCargo(cargoMapper.toEntity(orderDto.getCargo()));
        expected.setSourceCity(cityMapper.toEntity(orderDto.getSourceCity()));
        expected.setDestination(cityMapper.toEntity(orderDto.getDestination()));
        expected.setDeliveryWorkingDays(orderDto.getDeliveryWorkingDays());
        expected.setChangedAt(NOW);
        when(orderMapperSpy.getNow()).thenReturn(NOW);
        expected.setAssignedTruck(truckMapper.toEntity(orderDto.getAssignedTruck()));

        //run
        Order actual = orderMapperSpy.toEntityWithChangedAt(orderDto);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }
}
