package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.repository.entity.OrderStatus;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import com.metrarty.LogiWEB.service.exception.ValueIsInvalidException;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    public void checkOrderTruck(Truck assignedTruck) {
        if (isTruckIdNull(assignedTruck)) {
            throw new EntityNotFoundException("Truck is not assigned to order.");
        }
    }

    public void checkOrderStatus(String orderStatus) {
        if (isStatus(orderStatus)) {
            throw new ValueIsInvalidException("Order is completed.");
        }
    }

    private boolean isTruckIdNull(Truck assignedTruck) {
        return assignedTruck == null;
    }

    private boolean isStatus (String orderStatus) {
        return "COMPLETED".equals(orderStatus);
    }
}
