package com.metrarty.LogiWEB.service.mapper;

import com.metrarty.LogiWEB.boundary.model.DeliveryOrderDto;
import com.metrarty.LogiWEB.repository.entity.Cargo;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.repository.entity.DeliveryOrder;
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
public class DeliveryDeliveryOrderMapperTest {

    @InjectMocks
    private DeliveryOrderMapper deliveryOrderMapper;

    @Mock
    private CargoMapper cargoMapper;

    @Mock
    private CityMapper cityMapper;

    private static final Instant NOW = Instant.now();

    private DeliveryOrderMapper deliveryOrderMapperSpy;

    @Before
    public void init() {
        deliveryOrderMapperSpy = Mockito.spy(deliveryOrderMapper);
    }

    @Test
    public void testToDto() {
        //prepare
        Cargo cargo = new Cargo();
        City destination = new City();

        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setId(1L);
        deliveryOrder.setCargo(cargo);
        deliveryOrder.setDestination(destination);
        deliveryOrder.setDeliveryDate(LocalDate.of(2022, 01, 01));

        DeliveryOrderDto expected = new DeliveryOrderDto();
        expected.setId(deliveryOrder.getId());
        expected.setCargo(cargoMapper.toDto(deliveryOrder.getCargo()));
        expected.setDestination(cityMapper.toDto(deliveryOrder.getDestination()));
        expected.setDeliveryDate(deliveryOrder.getDeliveryDate());

        //run
        DeliveryOrderDto actual = deliveryOrderMapper.toDto(deliveryOrder);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @Test
    public void testToInitialEntity() {
        //prepare
        Cargo cargo = new Cargo();
        City destination = new City();

        DeliveryOrderDto deliveryOrderDto = new DeliveryOrderDto();
        deliveryOrderDto.setId(1L);
        deliveryOrderDto.setCargo(cargoMapper.toDto(cargo));
        deliveryOrderDto.setDestination(cityMapper.toDto(destination));
        deliveryOrderDto.setDeliveryDate(LocalDate.of(2022, 01, 01));

        DeliveryOrder expected = new DeliveryOrder();
        expected.setId(deliveryOrderDto.getId());
        expected.setCargo(cargoMapper.toEntity(deliveryOrderDto.getCargo()));
        expected.setDestination(cityMapper.toEntity(deliveryOrderDto.getDestination()));
        expected.setDeliveryDate(deliveryOrderDto.getDeliveryDate());
        expected.setCreatedAt(NOW);
        when(deliveryOrderMapperSpy.getNow()).thenReturn(NOW);

        //run
        DeliveryOrder actual = deliveryOrderMapperSpy.toEntityWithCreatedAt(deliveryOrderDto);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @Test
    public void testToUpdatedEntity() {
        //prepare
        Cargo cargo = new Cargo();
        City destination = new City();

        DeliveryOrderDto deliveryOrderDto = new DeliveryOrderDto();
        deliveryOrderDto.setId(1L);
        deliveryOrderDto.setCargo(cargoMapper.toDto(cargo));
        deliveryOrderDto.setDestination(cityMapper.toDto(destination));
        deliveryOrderDto.setDeliveryDate(LocalDate.of(2022, 01, 01));

        DeliveryOrder expected = new DeliveryOrder();
        expected.setId(deliveryOrderDto.getId());
        expected.setCargo(cargoMapper.toEntity(deliveryOrderDto.getCargo()));
        expected.setDestination(cityMapper.toEntity(deliveryOrderDto.getDestination()));
        expected.setDeliveryDate(deliveryOrderDto.getDeliveryDate());
        expected.setChangedAt(NOW);
        when(deliveryOrderMapperSpy.getNow()).thenReturn(NOW);

        //run
        DeliveryOrder actual = deliveryOrderMapperSpy.toEntityWithChangedAt(deliveryOrderDto);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }
}
