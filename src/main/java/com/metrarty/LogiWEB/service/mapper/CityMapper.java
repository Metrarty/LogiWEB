package com.metrarty.LogiWEB.service.mapper;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.repository.entity.City;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CityMapper {
    /**
     * Transfers data from city to city DTO
     * @param city city
     * @return city DTO
     */
    public CityDto toDto(@NonNull City city) {
        CityDto entity = new CityDto();
        entity.setId(city.getId());
        entity.setCityName(city.getCityName());
        return entity;
    }


    private City createEntityAndMapCommonFields(CityDto cityDto) {
        City entity = new City();
        entity.setId(cityDto.getId());
        entity.setCityName(cityDto.getCityName());
        return entity;
    }

    /**
     * Creates city, transfers common fields from city DTO to city and sets createdAt time.
     * @param cityDto
     * @return
     */
    public City toInitialEntity(@NonNull CityDto cityDto) {
        City entity = createEntityAndMapCommonFields(cityDto);
        entity.setCreatedAt(getNow());
        return entity;
    }

    /**
     * Creates city, transfers common fields from city DTO to city and sets changedAt time.
     * @param cityDto
     * @return
     */
    public City toUpdatedEntity(@NonNull CityDto cityDto) {
        City entity = createEntityAndMapCommonFields(cityDto);
        entity.setChangedAt(getNow());
        return entity;
    }

    protected Instant getNow() {
        return Instant.now();
    }

}
