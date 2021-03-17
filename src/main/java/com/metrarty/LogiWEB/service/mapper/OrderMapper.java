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

    @Autowired
    public OrderMapper(CargoMapper cargoMapper, CityMapper cityMapper) {
        this.cargoMapper = cargoMapper;
        this.cityMapper = cityMapper;
    }


    public OrderDto toDto(@NonNull Order order) {
        log.info("OrderMapper.toDto was called with {}", order);
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCargo(cargoMapper.toDto(order.getCargo()));
        dto.setDestination(cityMapper.toDto(order.getDestination()));
        dto.setDeliveryDate(order.getDeliveryDate());
        return dto;
    }

    public Order toEntity(@NonNull OrderDto orderDto) {
        log.info("OrderMapper.toEntity was called with {}", orderDto);
        Order entity = new Order();
        entity.setId(orderDto.getId());
        entity.setCargo(cargoMapper.toEntity(orderDto.getCargo()));
        entity.setDestination(cityMapper.toEntity(orderDto.getDestination()));
        entity.setDeliveryDate(orderDto.getDeliveryDate());
        return entity;
    }

    public Order toEntityWithCreatedAt(@NonNull OrderDto orderDto) {
        log.info("OrderMapper.toInitialEntity was called with {}", orderDto);
        Order entity = toEntity(orderDto);
        entity.setCreatedAt(getNow());
        return entity;
    }

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
