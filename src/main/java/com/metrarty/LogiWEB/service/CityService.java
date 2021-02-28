package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.repository.CityRepository;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.service.exception.CityNotFoundException;
import com.metrarty.LogiWEB.service.mapper.CityMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public CityDto createCity(@NonNull CityDto cityDto) {
        log.info("CityService.createCity was called with {}", cityDto);
        City entity = cityMapper.toEntityWithCreatedAt(cityDto);

        cityRepository.save(entity);
        return cityMapper.toDto(entity);
    }

    /**
     * Finds all exist cities.
     * @return List of cities DTO
     */
    public List<CityDto> findAllCities() {
        log.info("CityService.findAllCities was called");
        List<City> entities = cityRepository.findAll();
        List<CityDto> result = new ArrayList<>();
        for (City city : entities) {
            CityDto cityDto = cityMapper.toDto(city);
            result.add(cityDto);
        }
        return result;
    }

    /**
     * Edits city with exact ID.
     * @param cityDto City DTO
     * @param id City ID
     * @return Edited city DTO
     */
    public CityDto editCity(@NonNull CityDto cityDto, @NonNull Long id) {
        log.info("CityService.editCity was called with {} {}", cityDto, id);
        City city = cityMapper.toEntityWithChangedAt(cityDto);

        City entity = cityRepository.findById(id)
                .orElseThrow(()-> new CityNotFoundException("City with ID " + id + " is not found"));

        city.setCreatedAt(entity.getCreatedAt());
        city.setId(entity.getId());
        City saved = cityRepository.save(city);
        return cityMapper.toDto(saved);
    }

    /**
     * Deletes city, selected by id.
     * @param id city id
     */
    public void deleteCityById(@NonNull Long id) {
        log.info("CityService.deleteCityById was called with {}", id);
        cityRepository.deleteById(id);
    }

}
