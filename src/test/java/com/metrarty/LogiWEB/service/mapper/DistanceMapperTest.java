package com.metrarty.LogiWEB.service.mapper;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.repository.entity.Distance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;



@RunWith(MockitoJUnitRunner.class)
public class DistanceMapperTest {

    @InjectMocks
    private DistanceMapper distanceMapper;

    @Test
    public void testToDto() {
        //prepare
        City city1 = new City();
        city1.setId(1L);
        city1.setCityName("Omsk");
        City city2 = new City();
        city2.setId(2L);
        city2.setCityName("Tomsk");

        Distance distance = new Distance();
        distance.setId(3L);
        distance.setCity1(city1);
        distance.setCity2(city2);
        distance.setDistance(100L);

        DistanceDto expected = new DistanceDto();
        expected.setId(distance.getId());
        expected.setCity1(distance.getCity1());
        expected.setCity2(distance.getCity2());
        expected.setDistance(distance.getDistance());


        //run
        DistanceDto actual = distanceMapper.toDto(distance);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @Test
    public void testToEntity() {
        //prepare
        City city1 = new City();
        city1.setId(1L);
        city1.setCityName("Omsk");
        City city2 = new City();
        city2.setId(2L);
        city2.setCityName("Tomsk");

        DistanceDto distanceDto = new DistanceDto();
        distanceDto.setId(3L);
        distanceDto.setCity1(city1);
        distanceDto.setCity2(city2);
        distanceDto.setDistance(100L);

        Distance expected = new Distance();
        expected.setId(distanceDto.getId());
        expected.setCity1(distanceDto.getCity1());
        expected.setCity2(distanceDto.getCity2());
        expected.setDistance(distanceDto.getDistance());


        //run
        Distance actual = distanceMapper.toEntity(distanceDto);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }
}
