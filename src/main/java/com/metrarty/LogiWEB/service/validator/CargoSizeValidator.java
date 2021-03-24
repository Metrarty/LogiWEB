package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.service.exception.CargoSizeIsInvalidException;
import org.springframework.stereotype.Component;

@Component
public class CargoSizeValidator {

    public void apply(Long size) {
        if (isSizeLessOrEqualNull(size)) {
            throw new CargoSizeIsInvalidException("Cargo size is invalid");
        }
    }

    private boolean isSizeLessOrEqualNull(Long size) {
        return size <= 0;
    }
}
