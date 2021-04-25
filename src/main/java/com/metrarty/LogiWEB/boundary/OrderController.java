package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.boundary.model.OrderDto;
import com.metrarty.LogiWEB.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Order controller.
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class OrderController {

    private final OrderService orderService;

    /**
     * Creates new order.
     * @param orderDto order DTO.
     * @return created order
     */
    @PostMapping("/order/create/")
    public OrderDto createOrder(@Valid @RequestBody OrderDto orderDto) {
        log.info("OrderController.createOrder was called with {}", orderDto);
        return orderService.createOrder(orderDto);
    }

    /**
     * Receives all orders DTO.
     * @return List of all orders DTO
     */
    @GetMapping("/order/all/")
    public List<OrderDto> findAll() {
        log.info("OrderController.all was called");
        return orderService.findAllOrders();
    }

    /**
     * Edits exist order DTO, selected by ID.
     * @param orderDto order DTO
     * @param id ID of order that should be edited
     * @return edited order DTO
     */
    @PutMapping("/order/editbyid/{id}")
    public OrderDto editOrder(@Valid @RequestBody OrderDto orderDto, @PathVariable Long id) {
        log.info("OrderController.editOrder was called with {} {}", orderDto, id);
        return orderService.editOrder(orderDto, id);
    }

    /**
     * Deletes exist order, selected by ID.
     * @param id order ID
     * @return message with ID of deleted order
     */
    @DeleteMapping("/order/deletebyid/{id}")
    public String deleteOrderById(@PathVariable Long id) {
        log.info("OrderController.deleteOrderById was called with {}", id);
        orderService.deleteOrderById(id);
        return "Order with ID " + id + " is deleted";
    }

    @PatchMapping("/order/{orderId}/assign/truck/{truckId}")
    public OrderDto assignTruckToOrder(@PathVariable Long truckId, @PathVariable Long orderId) {
        log.info("OrderController.assignTruckToOrder was called with {}{}", truckId, orderId);
        return orderService.assignTruckToOrder(truckId, orderId);
    }

    @PatchMapping("/order/{orderId}/set/status/ontheway")
    public OrderDto setStatusOnTheWay(@PathVariable Long orderId) {
        log.info("OrderService.setStatusOnTheWay was called with {}", orderId);
        return orderService.setStatusOnTheWay(orderId);
    }
}
