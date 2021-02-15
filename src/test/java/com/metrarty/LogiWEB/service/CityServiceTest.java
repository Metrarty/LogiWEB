package com.metrarty.LogiWEB.service;


import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.repository.CityRepository;
import com.metrarty.LogiWEB.repository.entity.City;
//import com.metrarty.LogiWEB.service.exception.CityNotFoundException;
import com.metrarty.LogiWEB.service.exception.CityNotFoundException;
import com.metrarty.LogiWEB.service.mapper.CityMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    @InjectMocks
    private CityService cityService;

    @Mock
    private CityRepository cityRepositoryMock;

    @Mock
    private CityMapper cityMapperMock;

    private static final Instant NOW = Instant.now();
    private CityMapper cityMapper;

    @Before
    public void init() {
        cityMapper = new CityMapper();
    }

    @Test
    public void testCreateCity() {
        //prepare
        CityDto testCityDto = new CityDto();
        testCityDto.setId(1L);
        City testCity = cityMapperMock.toInitialEntity(testCityDto);
        //run
        cityService.createCity(testCityDto);
        //test
        verify(cityRepositoryMock, times(1)).save(testCity);
        verifyNoMoreInteractions(cityRepositoryMock);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateCity_WhenInputIsNull() {
        cityService.createCity(null);
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
        List<CityDto> actual = cityService.findAllCities();
        //test
        Assert.assertEquals("Must be equal", 1, actual.size());
        Assert.assertEquals("Must be equal", expectedDto, actual.get(0));
        verify(cityRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(cityRepositoryMock);
    }

    @Test
    public void testEditCity() {
        //prepare
        CityDto input = new CityDto();
        City city = new City();
        when(cityMapperMock.toUpdatedEntity(input)).thenReturn(city);

        City foundCity = new City();
        foundCity.setId(1L);
        foundCity.setCreatedAt(NOW);
        when(cityRepositoryMock.findById(1L)).thenReturn(java.util.Optional.of(foundCity));

        City saved = new City();
        saved.setId(1L);
        saved.setCreatedAt(NOW);
        when(cityRepositoryMock.save(saved)).thenReturn(saved);

        //run
        cityService.editCity(input, 1L);

        //test
        verify(cityMapperMock, times(1)).toUpdatedEntity(input);
        verify(cityRepositoryMock, times(1)).findById(1L);
        verify(cityRepositoryMock, times(1)).save(saved);
        verify(cityMapperMock, times(1)).toDto(saved);
    }

    @Test(expected = CityNotFoundException.class)
    public void testEditCity_whenOriginalNotFound() {
        //prepare
        CityDto input = new CityDto();
        City city = new City();
        when(cityMapperMock.toUpdatedEntity(input)).thenReturn(city);

        City foundCity = new City();
        foundCity.setId(1L);
        foundCity.setCreatedAt(NOW);
        when(cityRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        //run
        cityService.editCity(input, 1L);

        //test
    }

    @Test
    public void testDeleteCityById() {
        cityService.deleteCityById(1L);
    }
}
