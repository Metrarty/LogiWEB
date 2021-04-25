package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    public void checkOrderTruck(Truck assignedTruck) {
        if (isTruckIdNull(assignedTruck)) {
            throw new EntityNotFoundException("Truck is not assigned to order.");
        }
    }

    private boolean isTruckIdNull(Truck assignedTruck) {
        return assignedTruck == null;
    }
}
