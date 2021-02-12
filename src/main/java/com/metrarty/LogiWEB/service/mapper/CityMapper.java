package com.metrarty.LogiWEB.service.mapper;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.repository.entity.City;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CityMapper {
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

    public City toInitialEntity(@NonNull CityDto cityDto) {
        City entity = createEntityAndMapCommonFields(cityDto);
        entity.setCreatedAt(getNow());
        return entity;
    }

    public City toUpdatedEntity(@NonNull CityDto cityDto) {
        City entity = createEntityAndMapCommonFields(cityDto);
        entity.setChangedAt(getNow());
        return entity;
    }

    protected Instant getNow() {
        return Instant.now();
    }

}
