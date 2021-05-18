package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.OrderDto;
import com.metrarty.LogiWEB.repository.OrderRepository;
import com.metrarty.LogiWEB.repository.entity.Order;
import com.metrarty.LogiWEB.repository.entity.OrderStatus;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.repository.entity.TruckStatus;
import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import com.metrarty.LogiWEB.service.mapper.OrderMapper;
import com.metrarty.LogiWEB.service.validator.OrderValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Order service.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final TruckService truckService;
    private final DeliveryWorkingDaysCalculationService deliveryWorkingDaysCalculationService;
    private final OrderValidator orderValidator;
    private final CargoService cargoService;

    /**
     * Creates order and saves into repository.
     * @param orderDto order DTO
     * @return order
     */
    public OrderDto createOrder(@NonNull OrderDto orderDto) {
        log.info("OrderService.createOrder was called with {}", orderDto);
        Order entity = orderMapper.toEntityWithCreatedAt(orderDto);
        if (entity.getAssignedTruck() != null) {
            entity.setDeliveryWorkingDays(deliveryWorkingDaysCalculationService
                    .calculateDeliveryWorkingDays(entity));
            truckService.changeTruckStatus(entity.getAssignedTruck().getId(), TruckStatus.ASSIGNED.name());
        }
        entity.setOrderStatus(OrderStatus.CREATED.name());
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

        Order editedOrder = orderMapper.toEntityWithChangedAt(orderDto);
        Order originalOrder = findOneOrderById(id);
        orderValidator.checkOrderStatus(originalOrder.getOrderStatus());
        if (originalOrder.getAssignedTruck() != null) {
            editedOrder.setAssignedTruck(originalOrder.getAssignedTruck());
            editedOrder.setDeliveryWorkingDays(deliveryWorkingDaysCalculationService
                    .calculateDeliveryWorkingDays(editedOrder));
        }
        editedOrder.setCreatedAt(originalOrder.getCreatedAt());
        editedOrder.setId(originalOrder.getId());
        editedOrder.setOrderStatus(originalOrder.getOrderStatus());
        Order savedOrder = orderRepository.save(editedOrder);
        return orderMapper.toDto(savedOrder);
    }

    /**
     * Deletes order, selected by ID.
     * @param id order ID
     */
    public void deleteOrderById(@NonNull Long id) {
        log.info("OrderService.deleteOrderById was called with {}", id);
        orderRepository.deleteById(id);
    }

    /**
     * Assigns truck for order, selected by ID.
     * @param truckId truck ID
     * @param orderId order ID
     * @return order DTO with assigned truck
     */
    public OrderDto assignTruckToOrder(Long truckId, Long orderId) {
        Order order = findOneOrderById(orderId);
        Truck currentTruck = order.getAssignedTruck();

        if (currentTruck != null) {
            truckService.changeTruckStatus(currentTruck.getId(), TruckStatus.FREE.name());
        }

        truckService.changeTruckStatus(truckId, TruckStatus.ASSIGNED.name());

        Truck assignedTruck = truckService.findOneTruckById(truckId);
        order.setAssignedTruck(assignedTruck);
        order.setDeliveryWorkingDays(deliveryWorkingDaysCalculationService
                .calculateDeliveryWorkingDays(order));
        Order saved = orderRepository.save(order);
        return orderMapper.toDto(saved);
    }

    /**
     * Set status ON_THE_WAY for order, selected by ID.
     * @param orderId order ID
     * @return order DTO with status ON_THE_WAY
     */
    public OrderDto setStatusOnTheWay(Long orderId) {
        Order order = findOneOrderById(orderId);
        orderValidator.checkOrderTruck(order.getAssignedTruck());
        order.setOrderStatus(OrderStatus.ON_THE_WAY.name());
        order.setChangedAt(getNow());
        Order saved = orderRepository.save(order);
        return orderMapper.toDto(saved);
    }

    /**
     * Set status COMPLETED for order, selected by ID.
     * @param orderId order ID
     * @return order DTO with status COMPLETED
     */
    public OrderDto setStatusCompleted(Long orderId) {
        Order order = findOneOrderById(orderId);
        orderValidator.checkOrderStatus(order.getOrderStatus());
        order.setOrderStatus(OrderStatus.COMPLETED.name());
        order.setCompletedAt(getNow());
        truckService.changeTruckStatus(order.getAssignedTruck().getId(), TruckStatus.FREE.name());
        cargoService.setCargoDeliveredAt(order.getCargo().getId());
        Order saved  = orderRepository.save(order);
        return orderMapper.toDto(saved);
    }

    public OrderDto setStatusCancelled(Long orderId) {
        return null;
    }

    private Order findOneOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Order with ID " + id + " is not found"));
    }

    protected Instant getNow() {
        return Instant.now();
    }
}
