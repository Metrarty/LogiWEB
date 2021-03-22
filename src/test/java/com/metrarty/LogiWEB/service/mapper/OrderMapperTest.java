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

@RunWith(MockitoJUnitRunner.class)
public class OrderMapperTest {

    private static final Instant NOW = Instant.now();

    @InjectMocks
    private OrderMapper orderMapper;


    private OrderMapper orderMapperSpy;

    @Mock
    private CargoMapper cargoMapper;

    @Mock
    private CityMapper cityMapper;

    @Before
    public void init() {
        orderMapperSpy = Mockito.spy(orderMapper);
    }

    @Test
    public void testToDto() {
        //prepare

        Cargo cargo = new Cargo();
        City destination = new City();
        LocalDate date = LocalDate.now();

        Order order = new Order();
        order.setId(1L);
        order.setCargo(cargo);
        order.setDestination(destination);
       // order.setDeliveryDate(date);

        OrderDto expected = new OrderDto();
        expected.setId(order.getId());
        expected.setCargo(cargoMapper.toDto(order.getCargo()));
        expected.setDestination(cityMapper.toDto(order.getDestination()));
       // order.setDeliveryDate(order.getDeliveryDate());

        //run
        OrderDto actual = orderMapper.toDto(order);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }



}
