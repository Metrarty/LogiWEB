package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.service.exception.ValueIsInvalidException;
import org.junit.Before;
import org.junit.Test;

public class DistanceValidatorTest {
    private DistanceValidator distanceValidator;

    @Before
    public void init(){
        distanceValidator = new DistanceValidator();
    }

    @Test(expected = ValueIsInvalidException.class)
    public void checkDistanceWhenLessZero() {
        distanceValidator.checkDistance(-1L);
    }

    @Test
    public void checkDistanceWhenItIsPositive() {
        distanceValidator.checkDistance(1L);
    }
}
