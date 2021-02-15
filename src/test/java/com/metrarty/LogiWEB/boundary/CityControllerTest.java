package com.metrarty.LogiWEB.boundary;


import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.repository.CityRepository;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.service.CityService;
import com.metrarty.LogiWEB.service.mapper.CityMapper;
import org.hibernate.service.spi.InjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CityControllerTest {

    @InjectMocks
    private CityController cityController;

    @Mock
    private CityService cityService;

    @Mock
    private CityRepository cityRepositoryMock;

    @Mock
    private CityMapper cityMapperMock;

    @Test
    public void testCreateCity() {
        //prepare
        CityDto cityDto = new CityDto();
        //cityDto.setId(1L);
       // cityDto.setCityName("Ufa");
        //run
        cityController.createCity(cityDto);
        verify(cityService, times(1)).createCity(cityDto);
    }

    @Test
    public void testAll() {
        //prepare
        //run
        cityController.All();
        //test
        verify(cityService, times(1)).findAllCities();
    }

    @Test
    public void testEditCity() {
        //prepare
        CityDto cityDto = new CityDto();
        cityDto.setCityName("Ufa");
        //run
     //   cityController.editCity(cityDto, id)
        //test
    }
}
