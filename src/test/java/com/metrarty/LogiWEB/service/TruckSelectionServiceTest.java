package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.repository.TruckRepository;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.repository.entity.Distance;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.repository.entity.TruckStatus;
import com.metrarty.LogiWEB.service.mapper.TruckMapper;
import com.metrarty.LogiWEB.service.validator.CargoValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class TruckSelectionServiceTest {

    @InjectMocks
    private TruckSelectionService truckSelectionService;

    @Mock
    private CargoValidator cargoValidator;

    @Mock
    private CityService cityService;

    @Mock
    private DistanceService distanceService;

    @Mock
    private TruckRepository truckRepository;

    @Mock
    private TruckMapper truckMapper;

    @Test
    public void chooseTruckToDeliverTest() {
        //prepare
        CityDto cityOrderDto = new CityDto();
        cityOrderDto.setId(1L);
        Mockito.when(cityService.findCityById(1L)).thenReturn(cityOrderDto);

        Truck truckWithFreeStatus = new Truck();
        truckWithFreeStatus.setId(2L);
        truckWithFreeStatus.setTruckStatus("FREE");
        truckWithFreeStatus.setCapacity(5000L);
        List<Truck> trucksWithFreeStatus = Collections.singletonList(truckWithFreeStatus);
        Mockito.when(truckRepository.findAllByTruckStatus("FREE")).thenReturn(trucksWithFreeStatus);
        TruckDto truckWithFreeStatusDto = new TruckDto();
        truckWithFreeStatusDto.setId(2L);
        truckWithFreeStatusDto.setTruckStatus(TruckStatus.valueOf("FREE"));
        truckWithFreeStatusDto.setCapacity(5000L);
        CityDto truckLocation = new CityDto();
        truckLocation.setId(2L);
        truckWithFreeStatusDto.setLocation(truckLocation);
        Mockito.when(truckMapper.toDto(truckWithFreeStatus)).thenReturn(truckWithFreeStatusDto);

        DistanceDto distance = new DistanceDto();
        distance.setCity1(truckLocation);
        distance.setCity2(cityOrderDto);
        List<DistanceDto> distancesDto = Collections.singletonList(distance);
        Mockito.when(distanceService.prepareSuitableDistances(cityOrderDto)).thenReturn(distancesDto);

        //run
        truckSelectionService.chooseTruckToDeliver(1L, 500L);
        //test
        Mockito.verify(cargoValidator, Mockito.times(1)).checkCargo(500L);
        Mockito.verify(cityService, Mockito.times(1)).findCityById(1L);
        Mockito.verify(truckRepository, Mockito.times(1)).findAllByTruckStatus("FREE");
        Mockito.verify(truckMapper, Mockito.times(1)).toDto(truckWithFreeStatus);
        Mockito.verify(distanceService, Mockito.times(1)).prepareSuitableDistances(cityOrderDto);
        Mockito.verifyNoMoreInteractions(cargoValidator, cityService, truckRepository, truckMapper, distanceService);
    }

    @Test
    public void chooseTruckToDeliverTestWhenTruckInCityOrder() {
        //prepare
        CityDto cityOrderDto = new CityDto();
        cityOrderDto.setId(1L);
        Mockito.when(cityService.findCityById(1L)).thenReturn(cityOrderDto);

        Truck truckWithFreeStatus = new Truck();
        truckWithFreeStatus.setId(2L);
        truckWithFreeStatus.setTruckStatus("FREE");
        truckWithFreeStatus.setCapacity(5000L);
        List<Truck> trucksWithFreeStatus = Collections.singletonList(truckWithFreeStatus);
        Mockito.when(truckRepository.findAllByTruckStatus("FREE")).thenReturn(trucksWithFreeStatus);
        TruckDto truckWithFreeStatusDto = new TruckDto();
        truckWithFreeStatusDto.setId(2L);
        truckWithFreeStatusDto.setTruckStatus(TruckStatus.valueOf("FREE"));
        truckWithFreeStatusDto.setCapacity(5000L);
        CityDto truckLocation = new CityDto();
        truckLocation.setId(2L);
        truckWithFreeStatusDto.setLocation(cityOrderDto);
        Mockito.when(truckMapper.toDto(truckWithFreeStatus)).thenReturn(truckWithFreeStatusDto);

        //run
        truckSelectionService.chooseTruckToDeliver(1L, 500L);
        //test
        Mockito.verify(cargoValidator, Mockito.times(1)).checkCargo(500L);
        Mockito.verify(cityService, Mockito.times(1)).findCityById(1L);
        Mockito.verify(truckRepository, Mockito.times(1)).findAllByTruckStatus("FREE");
        Mockito.verify(truckMapper, Mockito.times(1)).toDto(truckWithFreeStatus);
        Mockito.verifyNoMoreInteractions(cargoValidator, cityService, truckRepository, truckMapper);
    }
}
