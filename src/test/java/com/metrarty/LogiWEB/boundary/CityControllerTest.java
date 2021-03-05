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
        verifyNoMoreInteractions(cityService);
    }

    @Test
    public void testFindAllCities() {
        cityController.findAll();
        verify(cityService, times(1)).findAllCities();
        verifyNoMoreInteractions(cityService);
    }

    @Test
    public void testEditCity() {
        CityDto cityDto = new CityDto();
        cityController.editCity(cityDto, 1L);
        verify(cityService, times(1)).editCity(cityDto, 1L);
        verifyNoMoreInteractions(cityService);
    }

    @Test
    public void testDeleteCityById() {
        cityController.deleteCityById(1L);
        verify(cityService, times(1)).deleteCityById(1L);
        verifyNoMoreInteractions(cityService);
    }
}
