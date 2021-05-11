package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import com.metrarty.LogiWEB.service.exception.ValueIsInvalidException;
import org.junit.Before;
import org.junit.Test;

public class OrderValidatorTest {
    private OrderValidator orderValidator;

    @Before
    public void init() {
        orderValidator = new OrderValidator();
    }

    @Test(expected = EntityNotFoundException.class)
    public void checkOrderTruckWhenTruckIsNull() {
        orderValidator.checkOrderTruck(null);
    }

    @Test
    public void checkOrderTruckWhenItIsAssigned() {
        //prepare
        Truck truck = new Truck();
        //run
        orderValidator.checkOrderTruck(truck);
    }

    @Test(expected = ValueIsInvalidException.class)
    public void checkOrderStatusWhenCompleted() {
        orderValidator.checkOrderStatus("COMPLETED");
    }

    @Test
    public void checkOrderStatusWhenCreated() {
        orderValidator.checkOrderStatus("CREATED");
    }
}
