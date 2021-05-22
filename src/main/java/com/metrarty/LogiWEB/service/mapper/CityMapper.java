package com.metrarty.LogiWEB.service.mapper;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.repository.entity.City;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * City mapper.
 */
@Component
@Log4j2
public class CityMapper {
    /**
     * Transfers data from city to city DTO
     * @param city city
     * @return city DTO
     */
    public CityDto toDto(@NonNull City city) {
        log.info("CityMapper.toDto was called with {}", city);
        CityDto entity = new CityDto();
        entity.setId(city.getId());
        entity.setCityName(city.getCityName());
        return entity;
    }

    /**
     * Transfers data from city DTO to city
     * @param cityDto city DTO
     * @return city
     */
    public City toEntity(CityDto cityDto) {
        log.info("CityMapper.toEntity was called with {}", cityDto);
        City entity = new City();
        entity.setId(cityDto.getId());
        entity.setCityName(cityDto.getCityName());
        return entity;
    }

    /**
     * Creates city, transfers common fields from city DTO to city and sets createdAt time.
     * @param cityDto city DTO
     * @return city
     */
    public City toEntityWithCreatedAt(@NonNull CityDto cityDto) {
        log.info("CityMapper.toEntityWithCreatedAt was called with {}", cityDto);
        City entity = toEntity(cityDto);
        entity.setCreatedAt(getNow());
        return entity;
    }

    /**
     * Creates city, transfers common fields from city DTO to city and sets changedAt time.
     * @param cityDto city DTO
     * @return city
     */
    public City toEntityWithChangedAt(@NonNull CityDto cityDto) {
        log.info("CityMapper.toEntityWithChangedAt was called with {}", cityDto);
        City entity = toEntity(cityDto);
        entity.setChangedAt(getNow());
        return entity;
    }

    protected Instant getNow() {
        return Instant.now();
    }
}
