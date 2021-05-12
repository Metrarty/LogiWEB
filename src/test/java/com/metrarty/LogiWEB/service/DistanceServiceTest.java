package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.repository.DistanceRepository;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.repository.entity.Distance;
import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import com.metrarty.LogiWEB.service.mapper.DistanceMapper;
import com.metrarty.LogiWEB.service.validator.DistanceValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DistanceServiceTest {

    @InjectMocks
    private DistanceService distanceService;

    @Mock
    private DistanceRepository distanceRepository;

    @Mock
    private DistanceMapper distanceMapper;

    @Mock
    private DistanceValidator distanceValidator;

    @Test
    public void testCreateDistance() {
        //prepare
        DistanceDto testDistanceDto = new DistanceDto();
        testDistanceDto.setDistance(100L);
        Distance testDistance = new Distance();
        when(distanceMapper.toEntity(testDistanceDto)).thenReturn(testDistance);

        //run
        distanceService.createDistance(testDistanceDto);

        //test
        verify(distanceValidator, times(1)).checkDistance(100L);
        verify(distanceRepository, times(1)).save(testDistance);
        verifyNoMoreInteractions(distanceRepository, distanceValidator);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateDistance_WhenInputIsNull() {
        distanceService.createDistance(null);
    }

    @Test
    public void testFindAllDistances() {
        //prepare
        Distance distance = new Distance();
        List<Distance> distances = Collections.singletonList(distance);
        when(distanceRepository.findAll()).thenReturn(distances);
        DistanceDto distanceDto = new DistanceDto();
        distanceDto.setId(1L);
        when(distanceMapper.toDto(distance)).thenReturn(distanceDto);
        DistanceDto expectedDto = new DistanceDto();
        expectedDto.setId(1L);
        //run
        List<DistanceDto> actual = distanceService.findAllDistances();
        //test
        Assert.assertEquals("Must be equal", 1, actual.size());
        Assert.assertEquals("Must be equal", expectedDto, actual.get(0));
        verify(distanceRepository, times(1)).findAll();
        verify(distanceMapper, times(1)).toDto(distance);
        verifyNoMoreInteractions(distanceRepository, distanceMapper);
    }

    @Test
    public void testEditDistance() {
        //prepare
        DistanceDto input = new DistanceDto();
        Distance distance = new Distance();
        when(distanceMapper.toEntity(input)).thenReturn(distance);

        Distance foundDistance = new Distance();
        foundDistance.setId(1L);
        when(distanceRepository.findById(1L)).thenReturn(Optional.of(foundDistance));

        Distance saved = new Distance();
        saved.setId(1L);
        when(distanceRepository.save(saved)).thenReturn(saved);

        //run
        distanceService.editDistance(input, 1L);

        //test
        verify(distanceMapper, times(1)).toEntity(input);
        verify(distanceRepository, times(1)).findById(1L);
        verify(distanceRepository, times(1)).save(saved);
        verify(distanceMapper, times(1)).toDto(saved);
        verifyNoMoreInteractions(distanceRepository, distanceMapper);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEditDistance_whenOriginalNotFound() {
        //prepare
        DistanceDto input = new DistanceDto();
        Distance distance = new Distance();
        when(distanceMapper.toEntity(input)).thenReturn(distance);

        Distance foundDistance = new Distance();
        foundDistance.setId(1L);
        when(distanceRepository.findById(1L)).thenReturn(Optional.empty());

        //run
        distanceService.editDistance(input, 1L);
    }

    @Test(expected = NullPointerException.class)
    public void testEditDistance_WhenInputIsNull() {
        distanceService.editDistance(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testEditDistance_WhenDistanceDtoIsNull() {
        distanceService.editDistance(null, 1L);
    }

    @Test(expected = NullPointerException.class)
    public void testEditDistance_WhenDistanceIdIsNull() {
        DistanceDto distanceDto = new DistanceDto();
        distanceService.editDistance(distanceDto, null);
    }

    @Test
    public void testDeleteDistanceById() {
        //run
        distanceService.deleteDistanceById(1L);

        //test
        verify(distanceRepository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(distanceRepository);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteDistanceById_WhenInputIsNull() {
        distanceService.deleteDistanceById(null);
    }

    @Test
    public void prepareSuitableDistancesTest() {
        //prepare
        CityDto testCityOrderDto = new CityDto();

        Distance distance = new Distance();
        List<Distance> distances = Collections.singletonList(distance);
        when(distanceRepository.findAll()).thenReturn(distances);
        DistanceDto distanceDto = new DistanceDto();
        distanceDto.setCity1(testCityOrderDto);
        when(distanceMapper.toDto(distance)).thenReturn(distanceDto);
        DistanceDto expectedDto = new DistanceDto();
        expectedDto.setCity1(testCityOrderDto);
        List<DistanceDto> expectedList = Collections.singletonList(expectedDto);
        //run
        List<DistanceDto> actual = distanceService.prepareSuitableDistances(testCityOrderDto);
        //test
        Assert.assertEquals("Must be equal", 1, actual.size());
        Assert.assertEquals("Must be equal", expectedList.get(0), actual.get(0));
        verify(distanceRepository, times(1)).findAll();
        verify(distanceMapper, times(1)).toDto(distance);
        verifyNoMoreInteractions(distanceRepository, distanceMapper);
    }

    @Test
    public void findDistanceBetweenCitiesTest() {
        //prepare
        City orderDestination = new City();
        orderDestination.setId(1L);
        City orderSourceCity = new City();
        orderSourceCity.setId(2L);
        //run
        distanceService.distanceBetweenCities(orderDestination, orderSourceCity);
        //test
        verify(distanceRepository, times(1)).findDistanceBetweenCities(1L, 2L);
        verifyNoMoreInteractions(distanceRepository);
    }
}