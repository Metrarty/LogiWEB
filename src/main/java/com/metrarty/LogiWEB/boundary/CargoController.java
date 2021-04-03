package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.boundary.model.CargoDto;
import com.metrarty.LogiWEB.service.CargoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class CargoController {

    private final CargoService cargoService;

    /**
     * Creates new cargo.
     * @param cargoDto cargo DTO
     * @return created cargo
     */
    @PostMapping("/cargo/create/")
    public CargoDto createCargo(@Valid @RequestBody CargoDto cargoDto) {
        log.info("CargoController.createCargo was called with {}", cargoDto);
        return cargoService.createCargo(cargoDto);
    }

    /**
     * Receives all cargos DTO.
     * @return List of all cities DTO
     */
    @GetMapping("/cargo/all/")
    public List<CargoDto> findAll() {
        log.info("CargoController.all was called");
        return cargoService.findAllCargos();
    }

    /**
     * Edits exist cargo DTO, selected by ID.
     * @param cargoDto cargo DTO
     * @param id ID of cargo that should be edited
     * @return edited cargo DTO
     */
    @PutMapping("/cargo/editbyid/{id}")
    public CargoDto editCargo(@Valid @RequestBody CargoDto cargoDto, @PathVariable Long id) {
        log.info("CargoController.editCargo was called with {} {}", cargoDto, id);
        return cargoService.editCargo(cargoDto, id);
    }

    /**
     * Deletes exist cargo, selected by ID.
     * @param id cargo ID
     * @return message with ID of deleted cargo
     */
    @DeleteMapping("/cargo/deletebyid/{id}")
    public String deleteCargoById(@PathVariable Long id) {
        log.info("CargoController.deleteCargoById was called with {}", id);
        cargoService.deleteCargoById(id);
        return "Cargo with ID " + id + " is deleted";
    }
}
