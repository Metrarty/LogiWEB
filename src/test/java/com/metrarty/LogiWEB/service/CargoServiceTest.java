package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.CargoDto;
import com.metrarty.LogiWEB.repository.CargoRepository;
import com.metrarty.LogiWEB.repository.entity.Cargo;
import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import com.metrarty.LogiWEB.service.mapper.CargoMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class CargoServiceTest {
    @InjectMocks
    private CargoService cargoService;

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private CargoMapper cargoMapper;

    private static final Instant NOW = Instant.now();

    @Test
    public void testCreateCargo() {
        //prepare
        CargoDto testCargoDto = new CargoDto();
        testCargoDto.setId(1L);
        Cargo testCargo = cargoMapper.toEntityWithCreatedAt(testCargoDto);
        //run
        cargoService.createCargo(testCargoDto);
        //test
        verify(cargoRepository, times(1)).save(testCargo);
        verifyNoMoreInteractions(cargoRepository);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateCargo_WhenInputIsNull() {
        cargoService.createCargo(null);
    }

    @Test
    public void testFindAllCargos() {
        //prepare
        Cargo cargo = new Cargo();
        List<Cargo> cargos = Collections.singletonList(cargo);
        when(cargoRepository.findAll()).thenReturn(cargos);
        CargoDto dto = new CargoDto();
        dto.setId(1L);
        when(cargoMapper.toDto(cargo)).thenReturn(dto);
        CargoDto expectedDto = new CargoDto();
        expectedDto.setId(1L);
        //run
        List<CargoDto> actual = cargoService.findAllCargos();
        //test
        Assert.assertEquals("Must be equal", 1, actual.size());
        Assert.assertEquals("Must be equal", expectedDto, actual.get(0));
        verify(cargoRepository, times(1)).findAll();
        verifyNoMoreInteractions(cargoRepository);
    }

    @Test
    public void testEditCargo() {
        //prepare
        CargoDto input = new CargoDto();
        Cargo cargo = new Cargo();
        when(cargoMapper.toEntityWithChangedAt(input)).thenReturn(cargo);

        Cargo foundCargo = new Cargo();
        foundCargo.setId(1L);
        foundCargo.setCreatedAt(NOW);
        when(cargoRepository.findById(1L)).thenReturn(java.util.Optional.of(foundCargo));

        Cargo saved = new Cargo();
        saved.setId(1L);
        saved.setCreatedAt(NOW);
        when(cargoRepository.save(saved)).thenReturn(saved);

        //run
        cargoService.editCargo(input, 1L);

        //test
        verify(cargoMapper, times(1)).toEntityWithChangedAt(input);
        verify(cargoRepository, times(1)).findById(1L);
        verify(cargoRepository, times(1)).save(saved);
        verify(cargoMapper, times(1)).toDto(saved);
        verifyNoMoreInteractions(cargoRepository, cargoMapper);
    }


    @Test(expected = EntityNotFoundException.class)
    public void testEditCargo_whenOriginalNotFound() {
        //prepare
        CargoDto input = new CargoDto();
        Cargo cargo = new Cargo();
        when(cargoMapper.toEntityWithChangedAt(input)).thenReturn(cargo);

        Cargo foundCargo = new Cargo();
        foundCargo.setId(1L);
        foundCargo.setCreatedAt(NOW);
        when(cargoRepository.findById(1L)).thenReturn(Optional.empty());

        //run
        cargoService.editCargo(input, 1L);
        verifyNoMoreInteractions(cargoRepository, cargoMapper);
    }

    @Test(expected = NullPointerException.class)
    public void testEditCargoById_WhenInputIsNull() {
        cargoService.editCargo(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testEditCargoById_WhenCargoDtoIsNull() {
        cargoService.editCargo(null, 1L);
    }

    @Test(expected = NullPointerException.class)
    public void testEditCargoById_WhenCargoIdIsNull() {
        CargoDto cargoDto = new CargoDto();
        cargoService.editCargo(cargoDto, null);
    }

    @Test
    public void testDeleteCargoById() {
        //run
        cargoService.deleteCargoById(1L);

        //test
        verify(cargoRepository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(cargoRepository);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteCargoById_WhenInputIsNull() {
        cargoService.deleteCargoById(null);
    }
}
