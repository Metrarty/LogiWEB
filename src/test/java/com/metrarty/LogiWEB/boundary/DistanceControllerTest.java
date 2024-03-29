package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.service.DistanceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DistanceControllerTest {

    @InjectMocks
    private DistanceController distanceController;

    @Mock
    private DistanceService distanceService;

    @Test
    public void testCreateDistance() {
        DistanceDto distanceDto = new DistanceDto();
        distanceController.createDistance(distanceDto);
        verify(distanceService, times(1)).createDistance(distanceDto);
        verifyNoMoreInteractions(distanceService);
    }

    @Test
    public void testFindAllDistances() {
        distanceController.findAll();
        verify(distanceService, times(1)).findAllDistances();
        verifyNoMoreInteractions(distanceService);
    }

    @Test
    public void testEditDistanceById() {
        DistanceDto distanceDto = new DistanceDto();
        distanceController.editDistance(distanceDto, 1L);
        verify(distanceService, times(1)).editDistance(distanceDto, 1L);
        verifyNoMoreInteractions(distanceService);
    }

    @Test
    public void testDeleteDistanceById() {
        distanceController.deleteDistanceById(1L);
        verify(distanceService, times(1)).deleteDistanceById(1L);
        verifyNoMoreInteractions(distanceService);
    }
}
