package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.boundary.model.DeliveryOrderDto;
import com.metrarty.LogiWEB.service.DeliveryOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Delivery order controller.
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class DeliveryOrderController {

    private final DeliveryOrderService deliveryOrderService;

    /**
     * Creates new delivery order.
     * @param deliveryOrderDto delivery order DTO.
     * @return created delivery order
     */
    @PostMapping("/order/create/")
    public DeliveryOrderDto createDeliveryOrder(@RequestBody DeliveryOrderDto deliveryOrderDto) {
        log.info("DeliveryOrderController.createDeliveryOrder was called with {}", deliveryOrderDto);
        return deliveryOrderService.createDeliveryOrder(deliveryOrderDto);
    }

    /**
     * Receives all delivery orders DTO.
     * @return List of all delivery orders DTO
     */
    @GetMapping("/order/all/")
    public List<DeliveryOrderDto> findAll() {
        log.info("DeliveryOrderController.all was called");
        return deliveryOrderService.findAllDeliveryOrders();
    }

    /**
     * Edits exist delivery order DTO, selected by ID.
     * @param deliveryOrderDto delivery order DTO
     * @param id ID of delivery order that should be edited
     * @return edited delivery order DTO
     */
    @PutMapping("/order/editbyid/{id}")
    public DeliveryOrderDto editDeliveryOrder(@RequestBody DeliveryOrderDto deliveryOrderDto, @PathVariable Long id) {
        log.info("DeliveryOrderController.editDeliveryOrder was called with {} {}", deliveryOrderDto, id);
        return deliveryOrderService.editDeliveryOrder(deliveryOrderDto, id);
    }

    /**
     * Deletes exist delivery order, selected by ID.
     * @param id delivery order ID
     * @return message with ID of deleted delivery order
     */
    @DeleteMapping("/order/deletebyid/{id}")
    public String deleteDeliveryOrderById(@PathVariable Long id) {
        log.info("DeliveryOrderController.deleteDeliveryOrderById was called with {}", id);
        deliveryOrderService.deleteDeliveryOrderById(id);
        return "Delivery order with ID " + id + " is deleted";
    }
}
