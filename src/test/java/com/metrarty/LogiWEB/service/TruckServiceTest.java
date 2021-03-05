package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.repository.TruckRepository;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.service.exception.TruckNotFoundException;
import com.metrarty.LogiWEB.service.mapper.TruckMapper;
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

    @Test
    public void testCreateTruck() {
        //prepare
        TruckDto testTruckDto = new TruckDto();
        testTruckDto.setId(1L);
        Truck testTruck = truckMapper.toEntity(testTruckDto);

        //run
        truckService.createTruck(testTruckDto);

        //test
        verify(truckRepository, times(1)).save(testTruck);
        verifyNoMoreInteractions(truckRepository);
        verifyNoMoreInteractions(truckMapper);
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
        verifyNoMoreInteractions(truckMapper);
    }

    @Test
    public void testEditTruck() {
        //prepare
        TruckDto input = new TruckDto();
        Truck truck = new Truck();
        when(truckMapper.toEntity(input)).thenReturn(truck);

        Truck foundTruck = new Truck();
        foundTruck.setId(1L);
        when(truckRepository.findById(1L)).thenReturn(Optional.of(foundTruck));

        Truck saved = new Truck();
        saved.setId(1L);
        when(truckRepository.save(saved)).thenReturn(saved);

        //run
        truckService.editTruck(input, 1L);

        //test
        verify(truckMapper, times(1)).toEntity(input);
        verify(truckRepository, times(1)).findById(1L);
        verify(truckRepository, times(1)).save(saved);
        verify(truckMapper, times(1)).toDto(saved);
        verifyNoMoreInteractions(truckRepository);
        verifyNoMoreInteractions(truckMapper);
    }

    @Test(expected = TruckNotFoundException.class)
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
}
