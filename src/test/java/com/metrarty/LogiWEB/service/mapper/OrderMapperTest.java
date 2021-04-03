package com.metrarty.LogiWEB.service.mapper;

import com.metrarty.LogiWEB.boundary.model.OrderDto;
import com.metrarty.LogiWEB.repository.entity.Cargo;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.repository.entity.Order;
import com.metrarty.LogiWEB.repository.entity.Truck;
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

    @Mock TruckMapper truckMapper;

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
        City destination = new City();
        Truck assignedTruck = new Truck();

        Order order = new Order();
        order.setId(1L);
        order.setCargo(cargo);
        order.setDestination(destination);
        order.setApproximatelyDeliveryDate(Instant.parse("2022-03-29T00:00:01.000+00:00"));
        order.setAssignedTruck(assignedTruck);

        OrderDto expected = new OrderDto();
        expected.setId(order.getId());
        expected.setCargo(cargoMapper.toDto(order.getCargo()));
        expected.setDestination(cityMapper.toDto(order.getDestination()));
        expected.setDeliveryDate(order.getApproximatelyDeliveryDate());
        expected.setAssignedTruck(truckMapper.toDto(order.getAssignedTruck()));

        //run
        OrderDto actual = orderMapper.toDto(order);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @Test
    public void testToInitialEntity() {
        //prepare
        Cargo cargo = new Cargo();
        City destination = new City();
        Truck assignedTruck = new Truck();

        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setCargo(cargoMapper.toDto(cargo));
        orderDto.setDestination(cityMapper.toDto(destination));
        orderDto.setDeliveryDate(Instant.parse("2022-03-29T00:00:01.000+00:00"));
        orderDto.setAssignedTruck(truckMapper.toDto(assignedTruck));

        Order expected = new Order();
        expected.setId(orderDto.getId());
        expected.setCargo(cargoMapper.toEntity(orderDto.getCargo()));
        expected.setDestination(cityMapper.toEntity(orderDto.getDestination()));
        expected.setApproximatelyDeliveryDate(orderDto.getDeliveryDate());
        expected.setCreatedAt(NOW);
        when(orderMapperSpy.getNow()).thenReturn(NOW);
        expected.setAssignedTruck(truckMapper.toEntity(orderDto.getAssignedTruck()));

        //run
        Order actual = orderMapperSpy.toEntityWithCreatedAt(orderDto);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @Test
    public void testToUpdatedEntity() {
        //prepare
        Cargo cargo = new Cargo();
        City destination = new City();
        Truck assignedTruck = new Truck();

        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setCargo(cargoMapper.toDto(cargo));
        orderDto.setDestination(cityMapper.toDto(destination));
        orderDto.setDeliveryDate(Instant.parse("2022-03-29T00:00:01.000+00:00"));
        orderDto.setAssignedTruck(truckMapper.toDto(assignedTruck));

        Order expected = new Order();
        expected.setId(orderDto.getId());
        expected.setCargo(cargoMapper.toEntity(orderDto.getCargo()));
        expected.setDestination(cityMapper.toEntity(orderDto.getDestination()));
        expected.setApproximatelyDeliveryDate(orderDto.getDeliveryDate());
        expected.setChangedAt(NOW);
        when(orderMapperSpy.getNow()).thenReturn(NOW);
        expected.setAssignedTruck(truckMapper.toEntity(orderDto.getAssignedTruck()));

        //run
        Order actual = orderMapperSpy.toEntityWithChangedAt(orderDto);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }
}
