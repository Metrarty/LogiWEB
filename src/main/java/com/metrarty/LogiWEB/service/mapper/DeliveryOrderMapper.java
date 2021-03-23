package com.metrarty.LogiWEB.service.mapper;


import com.metrarty.LogiWEB.boundary.model.DeliveryOrderDto;
import com.metrarty.LogiWEB.repository.entity.DeliveryOrder;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Log4j2
public class DeliveryOrderMapper {

    private final CargoMapper cargoMapper;
    private final CityMapper cityMapper;

    @Autowired
    public DeliveryOrderMapper(CityMapper cityMapper, CargoMapper cargoMapper) {
        this.cityMapper = cityMapper;
        this.cargoMapper = cargoMapper;
    }

    /**
     * Transfers data from delivery order to delivery order DTO.
     * @param deliveryOrder delivery order
     * @return delivery order DTO
     */
    public DeliveryOrderDto toDto(@NonNull DeliveryOrder deliveryOrder) {
        log.info("DeliveryOrderMapper.toDto was called with {}", deliveryOrder);
        DeliveryOrderDto dto = new DeliveryOrderDto();
        dto.setId(deliveryOrder.getId());
        dto.setCargo(cargoMapper.toDto(deliveryOrder.getCargo()));
        dto.setDestination(cityMapper.toDto(deliveryOrder.getDestination()));
        dto.setDeliveryDate(deliveryOrder.getDeliveryDate());
        return dto;
    }

    /**
     * Transfers data from delivery order DTO to delivery order
     * @param deliveryOrderDto delivery order DTO
     * @return delivery order
     */
    public DeliveryOrder toEntity(@NonNull DeliveryOrderDto deliveryOrderDto) {
        log.info("DeliveryOrderMapper.toEntity was called with {}", deliveryOrderDto);
        DeliveryOrder entity = new DeliveryOrder();
        entity.setId(deliveryOrderDto.getId());
        entity.setCargo(cargoMapper.toEntity(deliveryOrderDto.getCargo()));
        entity.setDestination(cityMapper.toEntity(deliveryOrderDto.getDestination()));
        entity.setDeliveryDate(deliveryOrderDto.getDeliveryDate());
        return entity;
    }

    /**
     * Creates delivery order, transfers common fields from delivery order DTO to delivery order and sets createdAt time.
     * @param deliveryOrderDto delivery order DTO
     * @return delivery order
     */
    public DeliveryOrder toEntityWithCreatedAt(@NonNull DeliveryOrderDto deliveryOrderDto) {
        log.info("DeliveryOrderMapper.toInitialEntity was called with {}", deliveryOrderDto);
        DeliveryOrder entity = toEntity(deliveryOrderDto);
        entity.setCreatedAt(getNow());
        return entity;
    }

    /**
     * Creates delivery order, transfers common fields from delivery order DTO to delivery order and sets changedAt time.
     * @param deliveryOrderDto delivery order DTO
     * @return delivery order
     */
    public DeliveryOrder toEntityWithChangedAt(@NonNull DeliveryOrderDto deliveryOrderDto) {
        log.info("DeliveryOrderMapper.toUpdatedEntity was called with {}", deliveryOrderDto);
        DeliveryOrder entity = toEntity(deliveryOrderDto);
        entity.setChangedAt(getNow());
        return entity;
    }

    protected Instant getNow() {
        return Instant.now();
    }
}
