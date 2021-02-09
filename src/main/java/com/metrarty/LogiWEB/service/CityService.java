package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.repository.CityRepository;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.service.mapper.CityMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public City createCity(@NonNull CityDto cityDto) {
        City entity = cityMapper.toEntity(cityDto);
        cityRepository.save(entity);
        return entity;
    }

    public List<CityDto> findAllCities() {
        List<City> entities = cityRepository.findAll();
        List<CityDto> result = new ArrayList<>();
        for (City city : entities) {
            CityDto cityDto = cityMapper.toDto(city);
            result.add(cityDto);
        }
        return result;
    }
}
