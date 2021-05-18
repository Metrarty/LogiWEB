package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.CargoDto;
import com.metrarty.LogiWEB.repository.CargoRepository;
import com.metrarty.LogiWEB.repository.entity.Cargo;
import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import com.metrarty.LogiWEB.service.mapper.CargoMapper;
import com.metrarty.LogiWEB.service.validator.CargoValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Cargo service.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class CargoService {

    private final CargoRepository cargoRepository;
    private final CargoMapper cargoMapper;
    private final CargoValidator cargoValidator;

    /**
     * Creates cargo and saves into repository.
     * @param cargoDto cargo DTO
     * @return cargo
     */
    public CargoDto createCargo(@NonNull CargoDto cargoDto) {
        log.info("CargoService.createCargo was called with {}", cargoDto);
        cargoValidator.checkCargo(cargoDto.getSize());
        Cargo entity = cargoMapper.toEntityWithCreatedAt(cargoDto);
        cargoRepository.save(entity);
        return cargoMapper.toDto(entity);
    }

    /**
     * Finds all exist cities.
     * @return List of cities DTO
     */
    public List<CargoDto> findAllCargos() {
        log.info("CargoService.findAllCargos was called");
        List<Cargo> entities = cargoRepository.findAll();
        List<CargoDto> result = new ArrayList<>();
        for (Cargo cargo : entities) {
            CargoDto cargoDto = cargoMapper.toDto(cargo);
            result.add(cargoDto);
        }
        return result;
    }

    /**
     * Edits cargo with exact ID.
     * @param cargoDto cargo DTO
     * @param id cargo ID
     * @return Edited cargo DTO
     */
    public CargoDto editCargo(@NonNull CargoDto cargoDto, @NonNull Long id) {
        log.info("CargoService.editCargo was called with {} {}", cargoDto, id);
        cargoValidator.checkCargo(cargoDto.getSize());
        Cargo editedCargo = cargoMapper.toEntityWithChangedAt(cargoDto);
        Cargo originalCargo = findOneCargoById(id);
        editedCargo.setCreatedAt(originalCargo.getCreatedAt());
        editedCargo.setId(originalCargo.getId());
        Cargo savedCargo = cargoRepository.save(editedCargo);
        return cargoMapper.toDto(savedCargo);
    }

    /**
     * Deletes cargo, selected by id.
     * @param id cargo id
     */
    public void deleteCargoById(@NonNull Long id) {
        log.info("CargoService.deleteCargoById was called with {}", id);
        cargoRepository.deleteById(id);
    }

    /**
     * Set deliveredAt for cargo, selected by ID.
     * @param id cargo ID
     */
    public void setCargoDeliveredAt(Long id) {
        Cargo originalCargo = findOneCargoById(id);
        originalCargo.setDeliveredAt(Instant.now());
        cargoRepository.save(originalCargo);
    }

    private Cargo findOneCargoById(Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Cargo with ID " + id + " is not found"));
    }
}
