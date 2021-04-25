package com.metrarty.LogiWEB.service.mapper;

import com.metrarty.LogiWEB.boundary.model.CargoDto;
import com.metrarty.LogiWEB.repository.entity.Cargo;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Log4j2
public class CargoMapper {
    /**
     * Transfers data from cargo to cargo DTO
     * @param cargo cargo
     * @return cargo DTO
     */
    public CargoDto toDto(@NonNull Cargo cargo) {
        log.info("CargoMapper.toDto was called with {}", cargo);
        CargoDto entity = new CargoDto();
        entity.setId(cargo.getId());
        entity.setSize(cargo.getSize());
        entity.setDeliveredAt(cargo.getDeliveredAt());
        return entity;
    }

    /**
     * Transfers data from cargo DTO to cargo
     * @param cargoDto
     * @return cargo
     */
    public Cargo toEntity(CargoDto cargoDto) {
        log.info("CargoMapper.toEntity was called with{}", cargoDto);
        Cargo entity = new Cargo();
        entity.setId(cargoDto.getId());
        entity.setSize(cargoDto.getSize());
        entity.setDeliveredAt(cargoDto.getDeliveredAt());
        return entity;
    }

    /**
     * Creates cargo, transfers common fields from cargo DTO to cargo and sets createdAt time.
     * @param cargoDto cargo DTO
     * @return cargo
     */
    public Cargo toEntityWithCreatedAt(@NonNull CargoDto cargoDto) {
        log.info("CargoMapper.toInitialEntity was called with {}", cargoDto);
        Cargo entity = toEntity(cargoDto);
        entity.setCreatedAt(getNow());
        return entity;
    }

    /**
     * Creates cargo, transfers common fields from cargo DTO to cargo and sets changedAt time.
     * @param cargoDto cargo DTO
     * @return cargo
     */
    public Cargo toEntityWithChangedAt(@NonNull CargoDto cargoDto) {
        log.info("CargoMapper.toUpdatedEntity was called with {}", cargoDto);
        Cargo entity = toEntity(cargoDto);
        entity.setChangedAt(getNow());
        return entity;
    }

    protected Instant getNow() {
        return Instant.now();
    }
}
