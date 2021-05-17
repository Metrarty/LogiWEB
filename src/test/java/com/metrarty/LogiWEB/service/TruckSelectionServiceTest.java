package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.repository.TruckRepository;
import com.metrarty.LogiWEB.repository.entity.City;
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
        List<TruckDto> allTrucksWithFreeStatusDto = Collections.singletonList(truckWithFreeStatusDto);


       // TruckDto truckSuitable = allTrucksWithFreeStatusDto.get(0);
        //Map<TruckDto, CityDto> trucksSuitable = Collections.singletonMap(truckSuitable, truckLocation);

       // TruckDto expected = result.get(0);

        //run
        truckSelectionService.chooseTruckToDeliver(1L, 500L);
        //test

    }
}
