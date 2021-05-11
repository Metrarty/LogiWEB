package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.service.exception.ValueIsInvalidException;
import org.junit.Before;
import org.junit.Test;

public class TruckValidatorTest {
    private TruckValidator truckValidator;

    @Before
    public void init() {
        truckValidator = new TruckValidator();
    }

    @Test(expected = ValueIsInvalidException.class)
    public void checkCapacitySizeWhenLessZero() {
        truckValidator.checkCapacitySize(-1L);
    }

    @Test
    public void checkCapacityWhenPositive() {
        truckValidator.checkCapacitySize(1L);
    }

    @Test(expected = ValueIsInvalidException.class)
    public void checkDistancePerDayWhenLessZero() {
        truckValidator.checkDistancePerDay(-1L);
    }

    @Test
    public void checkDistancePerDayWhenPositive() {
        truckValidator.checkDistancePerDay(1L);
    }
}
