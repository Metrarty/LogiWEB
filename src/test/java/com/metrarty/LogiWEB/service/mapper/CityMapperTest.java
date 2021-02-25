package com.metrarty.LogiWEB.service.mapper;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.repository.entity.City;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CityMapperTest {

    private static final Instant NOW = Instant.now();
    private CityMapper cityMapper;
    private CityMapper cityMapperSpy;

    @Before
    public void init() {
        cityMapper = new CityMapper();
        cityMapperSpy = Mockito.spy(cityMapper);
    }

    @Test
    public void testToDto() {
        //prepare
        City city = new City();
        city.setId(1L);
        city.setCityName("Ufa");

        CityDto expected = new CityDto();
        expected.setId(city.getId());
        expected.setCityName(city.getCityName());

        //run
        CityDto actual = cityMapper.toDto(city);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @Test
    public void testToInitialEntity() {
        //prepare
        CityDto cityDto = new CityDto();
        cityDto.setId(1L);
        cityDto.setCityName("Ufa");

        City expected = new City();
        expected.setId(cityDto.getId());
        expected.setCityName(cityDto.getCityName());
        expected.setCreatedAt(NOW);
        when(cityMapperSpy.getNow()).thenReturn(NOW);

        //run
        City actual = cityMapperSpy.toInitialEntity(cityDto);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @Test
    public void testToUpdatedEntity() {
        //prepare
        CityDto cityDto = new CityDto();
        cityDto.setId(1L);
        cityDto.setCityName("Ufa");

        City expected = new City();
        expected.setId(cityDto.getId());
        expected.setCityName(cityDto.getCityName());
        expected.setChangedAt(NOW);
        when(cityMapperSpy.getNow()).thenReturn(NOW);

        //run
        City actual = cityMapperSpy.toUpdatedEntity(cityDto);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }
}
