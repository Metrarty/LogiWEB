package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.OrderDto;
import com.metrarty.LogiWEB.repository.OrderRepository;
import com.metrarty.LogiWEB.repository.entity.Order;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import com.metrarty.LogiWEB.service.mapper.OrderMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final TruckService truckService;
    private final DeliveryWorkingDaysCalculationService deliveryWorkingDaysCalculationService;

    /**
     * Creates order and saves into repository.
     * @param orderDto order DTO
     * @return order
     */
    public OrderDto createOrder(@NonNull OrderDto orderDto) {
        log.info("OrderService.createOrder was called with {}", orderDto);
        Order entity = orderMapper.toEntityWithCreatedAt(orderDto);
        orderRepository.save(entity);
        return orderMapper.toDto(entity);
    }

    /**
     * Finds all exist orders.
     * @return List of orders DTO
     */
    public List<OrderDto> findAllOrders() {
        log.info("OrderService.findAllOrders was called");
        List<Order> entities = orderRepository.findAll();
        List<OrderDto> result = new ArrayList<>();
        for (Order order : entities) {
            OrderDto orderDto = orderMapper.toDto(order);
            result.add(orderDto);
        }
        return result;
    }

    /**
     * Edits order with exact ID.
     * @param orderDto order DTO
     * @param id order ID
     * @return Edited order DTO
     */
    public OrderDto editOrder(@NonNull OrderDto orderDto, @NonNull Long id) {
        log.info("OrderService.editOrder was called with {} {}", orderDto, id);

        Order order = orderMapper.toEntityWithChangedAt(orderDto);
        Order entity = findOneOrderById(id);
        if (entity.getAssignedTruck() != null) {
        order.setAssignedTruck(entity.getAssignedTruck());
        order.setDeliveryWorkingDays(deliveryWorkingDaysCalculationService
                .calculateDeliveryWorkingDays(order));
        }
        order.setCreatedAt(entity.getCreatedAt());
        order.setId(entity.getId());
        Order saved = orderRepository.save(order);
        return orderMapper.toDto(saved);
    }

    /**
     * Deletes order, selected by ID.
     * @param id order ID
     */
    public void deleteOrderById(@NonNull Long id) {
        log.info("OrderService.deleteOrderById was called with {}", id);
        orderRepository.deleteById(id);
    }

    private Order findOneOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Order with ID " + id + " is not found"));
    }

    public OrderDto assignTruckToOrder(Long truckId, Long orderId) {
        Order order = findOneOrderById(orderId);
        Truck currentTruck = order.getAssignedTruck();

        if (currentTruck != null) {
            truckService.changeTruckStatus(currentTruck.getId(), "FREE");
        }

        truckService.changeTruckStatus(truckId, "ASSIGNED");

        Truck assignedTruck = truckService.findOneTruckById(truckId);
        order.setAssignedTruck(assignedTruck);
        order.setDeliveryWorkingDays(deliveryWorkingDaysCalculationService
                .calculateDeliveryWorkingDays(order));
        Order saved = orderRepository.save(order);
        return orderMapper.toDto(saved);
    }
}
