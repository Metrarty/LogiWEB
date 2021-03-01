package com.metrarty.LogiWEB.service;


import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.repository.DistanceRepository;
import com.metrarty.LogiWEB.repository.entity.Distance;
import com.metrarty.LogiWEB.service.exception.DistanceNotFoundException;
import com.metrarty.LogiWEB.service.mapper.DistanceMapper;
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

    @Test
    public void testCreateDistance() {
        //prepare
        DistanceDto testDistanceDto = new DistanceDto();
        testDistanceDto.setId(1L);
        Distance testDistance = distanceMapper.toEntity(testDistanceDto);

        //run
        distanceService.createDistance(testDistanceDto);

        //test
        verify(distanceRepository, times(1)).save(testDistance);
        verifyNoMoreInteractions(distanceRepository);
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
        verifyNoMoreInteractions(distanceRepository);
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
    }

    @Test(expected = DistanceNotFoundException.class)
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
        distanceService.deleteDistanceById(1L);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteDistanceById_WhenInputIsNull() {
        distanceService.deleteDistanceById(null);
    }
}