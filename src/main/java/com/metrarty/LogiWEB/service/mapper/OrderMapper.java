package com.metrarty.LogiWEB.service.mapper;


import com.metrarty.LogiWEB.boundary.model.OrderDto;
import com.metrarty.LogiWEB.repository.entity.Order;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Log4j2
public class OrderMapper {

    private final CargoMapper cargoMapper;
    private final CityMapper cityMapper;
    private final TruckMapper truckMapper;

    @Autowired
    public OrderMapper(CityMapper cityMapper, CargoMapper cargoMapper, TruckMapper truckMapper) {
        this.cityMapper = cityMapper;
        this.cargoMapper = cargoMapper;
        this.truckMapper = truckMapper;
    }

    /**
     * Transfers data from order to order DTO.
     * @param order order
     * @return order DTO
     */
    public OrderDto toDto(@NonNull Order order) {
        log.info("OrderMapper.toDto was called with {}", order);
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCargo(cargoMapper.toDto(order.getCargo()));
        dto.setDestination(cityMapper.toDto(order.getDestination()));
        dto.setAssignedTruck(truckMapper.toDto(order.getAssignedTruck()));
        dto.setApproximatelyDeliveryDate(order.getApproximatelyDeliveryDate());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setChangedAt(order.getChangedAt());
        dto.setDeliveredAt(dto.getDeliveredAt());
        dto.setCompletedAt(dto.getCompletedAt());
        return dto;
    }

    /**
     * Transfers data from order DTO to order
     * @param orderDto order DTO
     * @return order
     */
    public Order toEntity(@NonNull OrderDto orderDto) {
        log.info("OrderMapper.toEntity was called with {}", orderDto);
        Order entity = new Order();
        entity.setId(orderDto.getId());
        entity.setCargo(cargoMapper.toEntity(orderDto.getCargo()));
        entity.setDestination(cityMapper.toEntity(orderDto.getDestination()));
        entity.setAssignedTruck(truckMapper.toEntity(orderDto.getAssignedTruck()));
        entity.setApproximatelyDeliveryDate(orderDto.getApproximatelyDeliveryDate());
        entity.setCreatedAt(orderDto.getCreatedAt());
        entity.setChangedAt(orderDto.getChangedAt());
        entity.setDeliveredAt(orderDto.getDeliveredAt());
        entity.setCompletedAt(orderDto.getCompletedAt());
        return entity;
    }

    /**
     * Creates order, transfers common fields from order DTO to order and sets createdAt time.
     * @param orderDto order DTO
     * @return order
     */
    public Order toEntityWithCreatedAt(@NonNull OrderDto orderDto) {
        log.info("OrderMapper.toInitialEntity was called with {}", orderDto);
        Order entity = toEntity(orderDto);
        entity.setCreatedAt(getNow());
        return entity;
    }

    /**
     * Creates order, transfers common fields from order DTO to order and sets changedAt time.
     * @param orderDto order DTO
     * @return order
     */
    public Order toEntityWithChangedAt(@NonNull OrderDto orderDto) {
        log.info("OrderMapper.toUpdatedEntity was called with {}", orderDto);
        Order entity = toEntity(orderDto);
        entity.setChangedAt(getNow());
        return entity;
    }

    protected Instant getNow() {
        return Instant.now();
    }
}
