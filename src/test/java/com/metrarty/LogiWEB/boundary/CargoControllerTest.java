package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.boundary.model.CargoDto;
import com.metrarty.LogiWEB.service.CargoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class CargoControllerTest {
    @InjectMocks
    private CargoController cargoController;

    @Mock
    private CargoService cargoService;

    @Test
    public void testCreateCargo() {
        CargoDto cargoDto = new CargoDto();
        cargoController.createCargo(cargoDto);
        verify(cargoService, times(1)).createCargo(cargoDto);
        verifyNoMoreInteractions(cargoService);
    }

    @Test
    public void testFindAllCargos() {
        cargoController.findAll();
        verify(cargoService, times(1)).findAllCargos();
        verifyNoMoreInteractions(cargoService);
    }

    @Test
    public void testEditCargo() {
        CargoDto cargoDto = new CargoDto();
        cargoController.editCargo(cargoDto, 1L);
        verify(cargoService, times(1)).editCargo(cargoDto, 1L);
        verifyNoMoreInteractions(cargoService);
    }

    @Test
    public void testDeleteCargoById() {
        cargoController.deleteCargoById(1L);
        verify(cargoService, times(1)).deleteCargoById(1L);
        verifyNoMoreInteractions(cargoService);
    }
}
