package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.service.CityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CityControllerTest {

    @InjectMocks
    private CityController cityController;

    @Mock
    private CityService cityService;

    @Test
    public void testCreateCity() {
        CityDto cityDto = new CityDto();
        cityController.createCity(cityDto);
        verify(cityService, times(1)).createCity(cityDto);
    }

    @Test
    public void testAll() {
        cityController.all();
        verify(cityService, times(1)).findAllCities();
    }

    @Test
    public void testEditCity() {
        CityDto cityDto = new CityDto();
        cityController.editCity(cityDto, 1L);
        verify(cityService, times(1)).editCity(cityDto, 1L);
    }

    @Test
    public void testDeleteCityById() {
        cityController.deleteCityById(1L);
        verify(cityService, times(1)).deleteCityById(1L);
    }
}
