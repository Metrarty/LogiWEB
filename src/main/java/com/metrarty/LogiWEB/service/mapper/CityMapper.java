package com.metrarty.LogiWEB.service.mapper;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.repository.entity.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {
    public CityDto toDto(City city) {
        CityDto entity = new CityDto();
        entity.setId(city.getId());
        entity.setCityName(city.getCityName());
        return entity;
    }

    public City toEntity(CityDto cityDto) {
        City entity = new City();
        entity.setId(cityDto.getId());
        entity.setCityName(cityDto.getCityName());
        return entity;
    }
}
