package com.metrarty.LogiWEB.service.mapper;


import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.repository.entity.Distance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DistanceMapperTest {

    @InjectMocks
    private DistanceMapper distanceMapper;

    @Mock
    private CityMapper cityMapper;

    @Test
    public void testToDto() {
        //prepare
        City city1 = new City();
        City city2 = new City();

        Distance distance = new Distance();
        distance.setId(1L);
        distance.setCity1(city1);
        distance.setCity2(city2);
        distance.setDistance(100L);

        DistanceDto expected = new DistanceDto();
        expected.setId(distance.getId());
        expected.setCity1(cityMapper.toDto(distance.getCity1()));
        expected.setCity2(cityMapper.toDto(distance.getCity2()));
        expected.setDistance(distance.getDistance());

        //run
        DistanceDto actual = distanceMapper.toDto(distance);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @Test
    public void testToEntity() {
        //prepare
        CityDto city1 = new CityDto();
        CityDto city2 = new CityDto();

        DistanceDto distanceDto = new DistanceDto();
        distanceDto.setId(1L);
        distanceDto.setCity1(city1);
        distanceDto.setCity2(city2);
        distanceDto.setDistance(100L);

        Distance expected = new Distance();
        expected.setId(distanceDto.getId());
        expected.setCity1(cityMapper.toEntity(distanceDto.getCity1()));
        expected.setCity2(cityMapper.toEntity(distanceDto.getCity2()));
        expected.setDistance(distanceDto.getDistance());

        //run
        Distance actual = distanceMapper.toEntity(distanceDto);

        //test
        Assert.assertEquals("Must be equals", expected, actual);
    }
}

