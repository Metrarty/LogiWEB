package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.repository.CargoRepository;
import com.metrarty.LogiWEB.service.exception.CargoNotFoundException;
import com.metrarty.LogiWEB.service.exception.CargoSizeIsInvalidException;
import org.springframework.stereotype.Component;

@Component
public class CargoValidator {

    private final CargoRepository cargoRepository;

    public CargoValidator(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }


    public void apply(Long size) {
        if (isSizeLessOrEqualNull(size)) {
            throw new CargoSizeIsInvalidException("Cargo size is invalid");
        }
    }

    private boolean isSizeLessOrEqualNull(Long size) {
        return size <= 0;
    }

    public void checkCargoExistence(Long id) {
        if (!isCargoExists(id)) {
            throw new CargoNotFoundException("Cargo with ID " + id + " is not found");
        }
    }

    private boolean isCargoExists(Long id) {
        return cargoRepository.existsById(id);
    }
}
