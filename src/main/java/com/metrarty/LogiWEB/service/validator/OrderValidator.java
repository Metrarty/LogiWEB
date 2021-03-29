package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.repository.OrderRepository;
import com.metrarty.LogiWEB.service.exception.ItemNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {
    private final OrderRepository orderRepository;

    public OrderValidator(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Throws (@link ItemNotFoundException) if order with ID is not exists.
     * @param id order ID
     */
    public void checkOrderExistence(Long id) {
        if (!isOrderExist(id)) {
            throw new ItemNotFoundException("Order with ID " + id + " is not found");
        }
    }

    private boolean isOrderExist(Long id) {
        return orderRepository.existsById(id);
    }
}
