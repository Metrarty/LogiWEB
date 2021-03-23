package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.DeliveryOrderDto;
import com.metrarty.LogiWEB.repository.DeliveryOrderRepository;
import com.metrarty.LogiWEB.repository.entity.DeliveryOrder;
import com.metrarty.LogiWEB.service.exception.DeliveryOrderNotFoundException;
import com.metrarty.LogiWEB.service.mapper.DeliveryOrderMapper;
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
public class DeliveryOrderService {

    private final DeliveryOrderRepository deliveryOrderRepository;
    private final DeliveryOrderMapper deliveryOrderMapper;

    /**
     * Creates delivery order and saves into repository.
     * @param deliveryOrderDto delivery order DTO
     * @return delivery order
     */
    public DeliveryOrderDto createDeliveryOrder(@NonNull DeliveryOrderDto deliveryOrderDto) {
        log.info("DeliveryOrderService.createDeliveryOrder was called with {}", deliveryOrderDto);
        DeliveryOrder entity = deliveryOrderMapper.toEntityWithCreatedAt(deliveryOrderDto);
        deliveryOrderRepository.save(entity);
        return deliveryOrderMapper.toDto(entity);
    }

    /**
     * Finds all exist delivery orders.
     * @return List of delivery orders DTO
     */
    public List<DeliveryOrderDto> findAllDeliveryOrders() {
        log.info("DeliveryOrderService.findAllDeliveryOrders was called");
        List<DeliveryOrder> entities = deliveryOrderRepository.findAll();
        List<DeliveryOrderDto> result = new ArrayList<>();
        for (DeliveryOrder deliveryOrder : entities) {
            DeliveryOrderDto deliveryOrderDto = deliveryOrderMapper.toDto(deliveryOrder);
            result.add(deliveryOrderDto);
        }
        return result;
    }

    /**
     * Edits delivery order with exact ID.
     * @param deliveryOrderDto delivery order DTO
     * @param id delivery order ID
     * @return Edited delivery order DTO
     */
    public DeliveryOrderDto editDeliveryOrder(@NonNull DeliveryOrderDto deliveryOrderDto, @NonNull Long id) {
        log.info("DeliveryOrderService.editDeliveryOrder was called with {} {}", deliveryOrderDto, id);
        DeliveryOrder deliveryOrder = deliveryOrderMapper.toEntityWithChangedAt(deliveryOrderDto);

        DeliveryOrder entity = deliveryOrderRepository.findById(id)
                .orElseThrow(()-> new DeliveryOrderNotFoundException("Delivery order with ID " + id + " is not found"));

        deliveryOrder.setCreatedAt(entity.getCreatedAt());
        deliveryOrder.setId(entity.getId());
        DeliveryOrder saved = deliveryOrderRepository.save(deliveryOrder);
        return deliveryOrderMapper.toDto(saved);
    }

    /**
     * Deletes delivery order, selected by ID.
     * @param id delivery order ID
     */
    public void deleteDeliveryOrderById(@NonNull Long id) {
        log.info("DeliveryOrderService.deleteDeliveryOrderById was called with {}", id);
        deliveryOrderRepository.deleteById(id);
    }

}
