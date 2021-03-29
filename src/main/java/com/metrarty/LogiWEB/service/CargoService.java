package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.CargoDto;
import com.metrarty.LogiWEB.repository.CargoRepository;
import com.metrarty.LogiWEB.repository.entity.Cargo;
import com.metrarty.LogiWEB.service.exception.ItemNotFoundException;
import com.metrarty.LogiWEB.service.mapper.CargoMapper;
import com.metrarty.LogiWEB.service.validator.CargoValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
     * @return List of cities DTO.
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
        Cargo cargo = cargoMapper.toEntityWithChangedAt(cargoDto);

        Cargo entity = cargoRepository.findById(id)
                .orElseThrow(()-> new ItemNotFoundException("Cargo with ID " + id + " is not found"));

        cargo.setCreatedAt(entity.getCreatedAt());
        cargo.setId(entity.getId());
        Cargo saved = cargoRepository.save(cargo);
        return cargoMapper.toDto(saved);
    }

    /**
     * Deletes cargo, selected by id.
     * @param id cargo id
     */
    public void deleteCargoById(@NonNull Long id) {
        log.info("CargoService.deleteCargoById was called with {}", id);
        cargoValidator.checkCargoExistence(id);
        cargoRepository.deleteById(id);
    }
}
