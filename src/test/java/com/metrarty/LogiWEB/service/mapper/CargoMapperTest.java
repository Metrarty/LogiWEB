package com.metrarty.LogiWEB.service.mapper;

import com.metrarty.LogiWEB.boundary.model.CargoDto;
import com.metrarty.LogiWEB.repository.entity.Cargo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CargoMapperTest {

    private static final Instant NOW = Instant.now();
    private CargoMapper cargoMapper;
    private CargoMapper cargoMapperSpy;

    @Before
    public void init() {
        cargoMapper = new CargoMapper();
        cargoMapperSpy = Mockito.spy(cargoMapper);
    }

    @Test
    public void testToDto() {
        //prepare
        Cargo cargo = new Cargo();
        cargo.setId(1L);
        cargo.setSize(100L);

        CargoDto expected = new CargoDto();
        expected.setId(cargo.getId());
        expected.setSize(cargo.getSize());

        //run
        CargoDto actual = cargoMapper.toDto(cargo);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @Test
    public void testToInitialEntity() {
        //prepare
        CargoDto cargoDto = new CargoDto();
        cargoDto.setId(1L);
        cargoDto.setSize(100L);

        Cargo expected = new Cargo();
        expected.setId(cargoDto.getId());
        expected.setSize(cargoDto.getSize());
        expected.setCreatedAt(NOW);
        when(cargoMapperSpy.getNow()).thenReturn(NOW);

        //run
        Cargo actual = cargoMapperSpy.toEntityWithCreatedAt(cargoDto);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @Test
    public void testToUpdatedEntity() {
        //prepare
        CargoDto cargoDto = new CargoDto();
        cargoDto.setId(1L);
        cargoDto.setSize(100L);

        Cargo expected = new Cargo();
        expected.setId(cargoDto.getId());
        expected.setSize(cargoDto.getSize());
        expected.setChangedAt(NOW);
        when(cargoMapperSpy.getNow()).thenReturn(NOW);

        //run
        Cargo actual = cargoMapperSpy.toEntityWithChangedAt(cargoDto);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }
}
