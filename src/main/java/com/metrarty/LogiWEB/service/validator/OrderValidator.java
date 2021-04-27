package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.repository.entity.OrderStatus;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import com.metrarty.LogiWEB.service.exception.ValueIsInvalidException;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    public void checkOrderTruck(Truck assignedTruck) {
        if (isTruckNotAssigned(assignedTruck)) {
            throw new EntityNotFoundException("Truck is not assigned to order.");
        }
    }

    public void checkOrderStatus(String orderStatus) {
        if (isStatusCompleted(orderStatus)) {
            throw new ValueIsInvalidException("Order is completed.");
        }
    }

    private boolean isTruckNotAssigned(Truck assignedTruck) {
        return assignedTruck == null;
    }

    private boolean isStatusCompleted(String orderStatus) {
        return OrderStatus.COMPLETED.name().equals(orderStatus);
    }
}
