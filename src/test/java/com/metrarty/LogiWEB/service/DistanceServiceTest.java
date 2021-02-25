package com.metrarty.LogiWEB.service;


import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.repository.DistanceRepository;
import com.metrarty.LogiWEB.repository.entity.Distance;
import com.metrarty.LogiWEB.service.mapper.DistanceMapper;
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


}