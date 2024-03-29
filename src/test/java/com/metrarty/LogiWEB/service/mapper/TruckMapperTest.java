package com.metrarty.LogiWEB.service.mapper;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.repository.entity.TruckStatus;
import com.metrarty.LogiWEB.repository.entity.Truck;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TruckMapperTest {

    @InjectMocks
    private TruckMapper truckMapper;

    @Mock
    private CityMapper cityMapper;

    @Test
    public void testToDto() {
        //prepare
        City location = new City();

        Truck truck = new Truck();
        truck.setId(1L);
        truck.setCapacity(500L);
        truck.setLocation(location);
        truck.setDistancePerDay(1000L);
        truck.setTruckStatus(TruckStatus.FREE.name());

        TruckDto expected = new TruckDto();
        expected.setId(truck.getId());
        expected.setCapacity(truck.getCapacity());
        expected.setLocation(null);
        expected.setDistancePerDay(truck.getDistancePerDay());
        expected.setTruckStatus(TruckStatus.valueOf(truck.getTruckStatus()));

        //run
        TruckDto actual = truckMapper.toDto(truck);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
        Mockito.verify(cityMapper, Mockito.times(1)).toDto(location);
        Mockito.verifyNoMoreInteractions(cityMapper);
    }

    @Test
    public void testToEntity() {
        //prepare
        CityDto locationDto = new CityDto();

        TruckDto truckDto = new TruckDto();
        truckDto.setId(1L);
        truckDto.setCapacity(500L);
        truckDto.setLocation(locationDto);
        truckDto.setDistancePerDay(1000L);
        truckDto.setTruckStatus(TruckStatus.FREE);

        Truck expected = new Truck();
        expected.setId(truckDto.getId());
        expected.setCapacity(truckDto.getCapacity());
        expected.setLocation(null);
        expected.setDistancePerDay(truckDto.getDistancePerDay());
        expected.setTruckStatus(String.valueOf(truckDto.getTruckStatus()));

        //run
        Truck actual = truckMapper.toEntity(truckDto);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
        Mockito.verify(cityMapper, Mockito.times(1)).toEntity(locationDto);
        Mockito.verifyNoMoreInteractions(cityMapper);
    }
}
