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
    public CityDto createCity(@NonNull CityDto cityDto) {
        City entity = cityMapper.toInitialEntity(cityDto);

        cityRepository.save(entity);
        return cityMapper.toDto(entity);
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
     * @return Response ok if city with requested id is found,
     */
    public ResponseEntity<CityDto> editCity(CityDto cityDto, Long id) {
        Optional<City> entity = cityRepository.findById(id);
        if (entity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            // TODO metrarty 12.02.2021: move to rest exception handler (look at weather stat project)
        }
        City city = cityMapper.toUpdatedEntity(cityDto);
        city.setCreatedAt(entity.get().getCreatedAt());
        city.setId(entity.get().getId());
        City saved = cityRepository.save(city);
        return ResponseEntity.ok(cityMapper.toDto(saved));
        // TODO metrarty 12.02.2021: move response entity to controller (service should return dto)
    }

    /**
     * Deletes city, selected by id.
     * @param id city id.
     */
    public void deleteCityById(Long id) {
        cityRepository.deleteById(id);
    }

}
