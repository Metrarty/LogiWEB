package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.service.exception.ValueIsInvalidException;
import org.junit.Before;
import org.junit.Test;

public class CargoValidatorTest {
    private CargoValidator cargoValidator;

    @Before
    public void init() {
        cargoValidator = new CargoValidator();
    }

    @Test(expected = ValueIsInvalidException.class)
    public void checkCargoWhenSizeLessZero() {
        cargoValidator.checkCargo(-1L);
    }

    @Test
    public void checkCargoWhenSizeIsPositive() {
        cargoValidator.checkCargo(1L);
    }
}
