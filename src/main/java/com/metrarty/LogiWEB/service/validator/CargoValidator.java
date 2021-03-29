package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.repository.CargoRepository;
import com.metrarty.LogiWEB.service.exception.ItemNotFoundException;
import com.metrarty.LogiWEB.service.exception.ValueIsInvalidException;
import org.springframework.stereotype.Component;

@Component
public class CargoValidator {

    private final CargoRepository cargoRepository;

    public CargoValidator(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    /**
     * Throws (@link ValueIsInvalidException) if cargo size if less or equal null.
     * @param size Cargo size
     */
    public void checkCargo(Long size) {
        if (isSizeLessOrEqualNull(size)) {
            throw new ValueIsInvalidException("Cargo size is invalid");
        }
    }

    /**
     * Throws (@link ItemNotFoundException) if cargo with ID is not exists.
     * @param id cargo ID
     */
    public void checkCargoExistence(Long id) {
        if (!isCargoExists(id)) {
            throw new ItemNotFoundException("Cargo with ID " + id + " is not found");
        }
    }

    private boolean isSizeLessOrEqualNull(Long size) {
        return size <= 0;
    }

    private boolean isCargoExists(Long id) {
        return cargoRepository.existsById(id);
    }
}
