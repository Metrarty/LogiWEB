package com.metrarty.LogiWEB.service.mapper;

import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.repository.entity.Truck;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Truck mapper.
 */
@Component
@Log4j2
public class TruckMapper {

    private CityMapper cityMapper;

    @Autowired
    public TruckMapper(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    /**
     * Transfers data from truck to truck DTO.
     * @param truck truck
     * @return truck DTO
     */
    public TruckDto toDto(@NonNull Truck truck) {
        log.info("TruckMapper.toDto was called with {}", truck);
        TruckDto dto = new TruckDto();
        dto.setId(truck.getId());
        dto.setCapacity(truck.getCapacity());
        dto.setLocation(cityMapper.toDto(truck.getLocation()));
        dto.setDistancePerDay(truck.getDistancePerDay());
        return dto;
    }

    /**
     * Transfers data from truck DTO to truck
     * @param truckDto truck DTO
     * @return truck
     */
    public Truck toEntity(@NonNull TruckDto truckDto) {
        log.info("TruckMapper.toEntity was called with {}", truckDto);
        Truck entity = new Truck();
        entity.setId(truckDto.getId());
        entity.setCapacity(truckDto.getCapacity());
        entity.setLocation(cityMapper.toEntity(truckDto.getLocation()));
        entity.setDistancePerDay(truckDto.getDistancePerDay());
        return entity;
    }

}
