package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.repository.entity.OrderStatus;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import com.metrarty.LogiWEB.service.exception.ValueIsInvalidException;
import org.springframework.stereotype.Component;

/**
 * Order validator.
 */
@Component
public class OrderValidator {

    /**
     * Throws (@link EntityNotFoundException) if truck is not assigned.
     * @param assignedTruck assigned truck
     */
    public void checkOrderTruck(Truck assignedTruck) {
        if (isTruckNotAssigned(assignedTruck)) {
            throw new EntityNotFoundException("Truck is not assigned to order.");
        }
    }

    /**
     * Throws (@link ValueIsInvalidException) if order status is COMPLETED.
     * @param orderStatus order status
     */
    public void checkOrderStatus(String orderStatus) {
        if (isStatusCompleted(orderStatus)) {
            throw new ValueIsInvalidException("Order is completed.");
        }
        if (isStatusCancelled(orderStatus)) {
            throw new ValueIsInvalidException("Order is cancelled.");
        }
    }

    private boolean isTruckNotAssigned(Truck assignedTruck) {
        return assignedTruck == null;
    }

    private boolean isStatusCompleted(String orderStatus) {
        return OrderStatus.COMPLETED.name().equals(orderStatus);
    }

    private boolean isStatusCancelled(String orderStatus) {
        return OrderStatus.CANCELLED.name().equals(orderStatus);
    }
}
