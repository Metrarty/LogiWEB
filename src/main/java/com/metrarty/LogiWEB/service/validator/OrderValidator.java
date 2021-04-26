package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.repository.entity.OrderStatus;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.repository.entity.TruckStatus;
import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import com.metrarty.LogiWEB.service.exception.ValueIsInvalidException;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    public void checkOrderTruck(Truck assignedTruck) {
        if (isAssignedTruckNull(assignedTruck)) {
            throw new EntityNotFoundException("Truck is not assigned to order.");
        }
    }

    public void checkOrderStatus(String orderStatus) {
        if (isStatus(orderStatus)) {
            throw new ValueIsInvalidException("Order is completed.");
        }
    }

    private boolean isAssignedTruckNull(Truck assignedTruck) {
        return assignedTruck == null;
    }

    private boolean isStatus (String orderStatus) {
        return OrderStatus.COMPLETED.name().equals(orderStatus);
    }
}
