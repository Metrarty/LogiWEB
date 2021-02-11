package com.metrarty.LogiWEB.service;


import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.repository.CityRepository;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.service.mapper.CityMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    @InjectMocks
    private CityService cityServiceMock;

    @Mock
    private CityRepository cityRepositoryMock;

    @Mock
    private CityMapper cityMapperMock;

    @Test
    public void testCreateUser() {
        //prepare
        CityDto testCityDto = new CityDto();
        testCityDto.setId(1L);
        City testCity = cityMapperMock.toEntity(testCityDto);
        //run
        cityServiceMock.createCity(testCityDto);
        //test
        verify(cityRepositoryMock, times(1)).save(testCity);
        verifyNoMoreInteractions(cityRepositoryMock);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateCity_WhenInputIsNull() {
        cityServiceMock.createCity(null);
    }

    @Test
    public void testFindAllCities() {
        //prepare
        City city = new City();
        List<City> cities = Collections.singletonList(city);
        when(cityRepositoryMock.findAll()).thenReturn(cities);
        CityDto dto = new CityDto();
        dto.setCityName("Ufa");
        when(cityMapperMock.toDto(city)).thenReturn(dto);
        CityDto expectedDto = new CityDto();
        expectedDto.setCityName("Ufa");
        //run
        List<CityDto> actual = cityServiceMock.findAllCities();
        //test
        Assert.assertEquals("Must be equal", 1, actual.size());
        Assert.assertEquals("Must be equal", expectedDto, actual.get(0));
        verify(cityRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(cityRepositoryMock);
    }

    @Test
    public void testDeleteCityById() {
        //prepare
        //run
        cityRepositoryMock.deleteById(1L);
        //test
        verify(cityRepositoryMock, times(1)).deleteById(1L);
    }
}
