package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.repository.CityRepository;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.service.mapper.CityMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * City service.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    /**
     * Creates city and saves into repository.
     * @param cityDto City DTO
     * @return City
     */
    public City createCity(@NonNull CityDto cityDto) {
        City entity = cityMapper.toEntity(cityDto);
        cityRepository.save(entity);
        return entity;
    }

    /**
     * Finds all exist cities.
     * @return List of cities DTO
     */
    public List<CityDto> findAllCities() {
        List<City> entities = cityRepository.findAll();
        List<CityDto> result = new ArrayList<>();
        for (City city : entities) {
            CityDto cityDto = cityMapper.toDto(city);
            result.add(cityDto);
        }
        return result;
    }

    /**
     *
     * @param cityDto
     * @param id
     * @return
     */
    public ResponseEntity editCity(CityDto cityDto, Long id) {
        Optional<City> entity = cityRepository.findById(id);
        if (entity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        entity.get().setCityName(cityDto.getCityName());
        cityRepository.save(entity.get());
        return ResponseEntity.ok(entity.get());
    }

    /**
     * Deletes city, selected by id.
     * @param id city id.
     */
    public void deleteCityById(Long id) {
        cityRepository.deleteById(id);
    }

}
