package com.metrarty.LogiWEB.service.mapper;

import com.metrarty.LogiWEB.boundary.model.CargoDto;
import com.metrarty.LogiWEB.boundary.model.OrderDto;
import com.metrarty.LogiWEB.repository.entity.Cargo;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.repository.entity.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.time.LocalDate;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderMapperTest {

    @InjectMocks
    private OrderMapper orderMapper;

    @Mock
    private CargoMapper cargoMapper;

    @Mock
    private CityMapper cityMapper;

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

        Order order = new Order();
        order.setId(1L);
        order.setCargo(cargo);
        order.setDestination(destination);
        order.setDeliveryDate(LocalDate.of(2022, 01, 01));

        OrderDto expected = new OrderDto();
        expected.setId(order.getId());
        expected.setCargo(cargoMapper.toDto(order.getCargo()));
        expected.setDestination(cityMapper.toDto(order.getDestination()));
        expected.setDeliveryDate(order.getDeliveryDate());

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

        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setCargo(cargoMapper.toDto(cargo));
        orderDto.setDestination(cityMapper.toDto(destination));
        orderDto.setDeliveryDate(LocalDate.of(2022, 01, 01));

        Order expected = new Order();
        expected.setId(orderDto.getId());
        expected.setCargo(cargoMapper.toEntity(orderDto.getCargo()));
        expected.setDestination(cityMapper.toEntity(orderDto.getDestination()));
        expected.setDeliveryDate(orderDto.getDeliveryDate());
        expected.setCreatedAt(NOW);
        when(orderMapperSpy.getNow()).thenReturn(NOW);

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

        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setCargo(cargoMapper.toDto(cargo));
        orderDto.setDestination(cityMapper.toDto(destination));
        orderDto.setDeliveryDate(LocalDate.of(2022, 01, 01));

        Order expected = new Order();
        expected.setId(orderDto.getId());
        expected.setCargo(cargoMapper.toEntity(orderDto.getCargo()));
        expected.setDestination(cityMapper.toEntity(orderDto.getDestination()));
        expected.setDeliveryDate(orderDto.getDeliveryDate());
        expected.setChangedAt(NOW);
        when(orderMapperSpy.getNow()).thenReturn(NOW);

        //run
        Order actual = orderMapperSpy.toEntityWithChangedAt(orderDto);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }
}
