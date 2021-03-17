package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.service.TruckService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TruckControllerTest {

    @InjectMocks
    private TruckController truckController;

    @Mock
    private TruckService truckService;

    @Test
    public void testCreateTruck() {
        TruckDto truckDto = new TruckDto();
        truckController.createTruck(truckDto);
        verify(truckService, times(1)).createTruck(truckDto);
        verifyNoMoreInteractions(truckService);
    }

    @Test
    public void testFindAllTrucks() {
        truckController.findAll();
        verify(truckService, times(1)).findAllTrucks();
        verifyNoMoreInteractions(truckService);
    }

    @Test
    public void testEditTruckById() {
        TruckDto truckDto = new TruckDto();
        truckController.editTruck(truckDto, 1L);
        verify(truckService, times(1)).editTruck(truckDto, 1L);
        verifyNoMoreInteractions(truckService);
    }

    @Test
    public void testDeleteTruckById() {
        truckController.deleteTruckById(1L);
        verify(truckService, times(1)).deleteTruckById(1L);
        verifyNoMoreInteractions(truckService);
    }

    @Test
    public void testChooseTrucktoDeliver() {
        truckController.chooseTruckToDeliver(1L, 100L);
        verify(truckService, times(1)).chooseTruckToDeliver(1L, 100L);
        verifyNoMoreInteractions(truckService);
    }
}
