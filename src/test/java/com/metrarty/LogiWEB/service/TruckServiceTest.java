package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.repository.TruckRepository;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import com.metrarty.LogiWEB.service.mapper.TruckMapper;
import com.metrarty.LogiWEB.service.validator.TruckValidator;
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
public class TruckServiceTest {

    @InjectMocks
    private TruckService truckService;

    @Mock
    private TruckRepository truckRepository;

    @Mock
    private TruckMapper truckMapper;

    @Mock
    private TruckValidator truckValidator;

    @Mock TruckSelectionService truckSelectionService;

    @Test
    public void testCreateTruck() {
        //prepare
        TruckDto testTruckDto = new TruckDto();
        testTruckDto.setCapacity(1000L);
        testTruckDto.setDistancePerDay(5000L);
        Truck testTruck = new Truck();
        when(truckMapper.toEntity(testTruckDto)).thenReturn(testTruck);

        //run
        truckService.createTruck(testTruckDto);

        //test
        verify(truckValidator, times(1)).checkCapacitySize(1000L);
        verify(truckValidator, times(1)).checkDistancePerDay(5000L);
        verify(truckRepository, times(1)).save(testTruck);
        verifyNoMoreInteractions(truckRepository, truckValidator);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateTruck_WhenInputIsNull() {
        truckService.createTruck(null);
    }

    @Test
    public void testFindAllTrucks() {
        //prepare
        Truck truck = new Truck();
        List<Truck> trucks = Collections.singletonList(truck);
        when(truckRepository.findAll()).thenReturn(trucks);
        TruckDto truckDto = new TruckDto();
        truckDto.setId(1L);
        when(truckMapper.toDto(truck)).thenReturn(truckDto);
        TruckDto expectedDto = new TruckDto();
        expectedDto.setId(1L);
        //run
        List<TruckDto> actual = truckService.findAllTrucks();
        //test
        Assert.assertEquals("Must be equal", 1, actual.size());
        Assert.assertEquals("Must be equal", expectedDto, actual.get(0));
        verify(truckRepository, times(1)).findAll();
        verifyNoMoreInteractions(truckRepository);
    }

    @Test
    public void testEditTruck() {
        //prepare
        TruckDto inputDto = new TruckDto();
        Truck editedTruck = new Truck();
        when(truckMapper.toEntity(inputDto)).thenReturn(editedTruck);

        Truck originalTruck = new Truck();
        originalTruck.setId(1L);
        when(truckRepository.findById(1L)).thenReturn(Optional.of(originalTruck));

        Truck saved = new Truck();
        saved.setId(1L);
        when(truckRepository.save(editedTruck)).thenReturn(saved);

        //run
        truckService.editTruck(inputDto, 1L);

        //test
        verify(truckMapper, times(1)).toEntity(inputDto);
        verify(truckRepository, times(1)).findById(1L);
        verify(truckRepository, times(1)).save(editedTruck);
        verify(truckMapper, times(1)).toDto(saved);
        verifyNoMoreInteractions(truckRepository, truckMapper);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEditTruck_whenOriginalNotFound() {
        //prepare
        TruckDto input = new TruckDto();
        Truck truck = new Truck();
        when(truckMapper.toEntity(input)).thenReturn(truck);

        Truck foundTruck = new Truck();
        foundTruck.setId(1L);
        when(truckRepository.findById(1L)).thenReturn(Optional.empty());

        //run
        truckService.editTruck(input, 1L);
        verifyNoMoreInteractions(truckRepository);
        verifyNoMoreInteractions(truckMapper);
    }

    @Test(expected = NullPointerException.class)
    public void testEditTruck_WhenInputIsNull() {
        truckService.editTruck(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testEditTruck_WhenTruckDtoIsNull() {
        truckService.editTruck(null, 1L);
    }

    @Test(expected = NullPointerException.class)
    public void testEditTruck_WhenTruckIdIsNull() {
        TruckDto truckDto = new TruckDto();
        truckService.editTruck(truckDto, null);
    }

    @Test
    public void testDeleteTruckById() {
        //run
        truckService.deleteTruckById(1L);

        //test
        verify(truckRepository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(truckRepository);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteTruckById_WhenInputIsNull() {
        truckService.deleteTruckById(null);
    }

    @Test
    public void chooseTruckToDeliverTest() {
        //prepare
        Truck truck = new Truck();
        truck.setId(1L);
        //run
        truckService.chooseTruckToDeliver(1L, 500L);
        //test
        verify(truckSelectionService, times(1)).chooseTruckToDeliver(1L, 500L);
        verifyNoMoreInteractions(truckSelectionService);
    }

    @Test
    public void changeTruckStatusTest() {
        //prepare
        Truck truck = new Truck();
        truck.setId(1L);
        when(truckRepository.findById(1L)).thenReturn(Optional.of(truck));

        Truck saved = new Truck();
        saved.setId(1L);
        saved.setTruckStatus("ASSIGNED");
        when(truckRepository.save(truck)).thenReturn(saved);
        //run
        truckService.changeTruckStatus(1L, "ASSIGNED");
        //test
        verify(truckRepository, times(1)).findById(1L);
        verify(truckRepository, times(1)).save(truck);
        verifyNoMoreInteractions(truckRepository);
    }
}
