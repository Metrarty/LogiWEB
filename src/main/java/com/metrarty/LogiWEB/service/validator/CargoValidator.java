package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.service.exception.ValueIsInvalidException;
import org.springframework.stereotype.Component;

@Component
public class CargoValidator {

    /**
     * Throws (@link ValueIsInvalidException) if cargo size if less or equal null.
     * @param size Cargo size
     */
    public void checkCargo(Long size) {
        if (isSizeLessOrEqualZero(size)) {
            throw new ValueIsInvalidException("Cargo size is invalid");
        }
    }

    private boolean isSizeLessOrEqualZero(Long size) {
        return size <= 0;
    }
}
