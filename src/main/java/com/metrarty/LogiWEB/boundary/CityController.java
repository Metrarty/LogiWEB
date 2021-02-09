package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.repository.CityRepository;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.service.CityService;
import com.metrarty.LogiWEB.service.mapper.CityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CityController {
    private final CityRepository cityRepository;
    private final CityService cityService;
    private final CityMapper cityMapper;

    @PostMapping("/city/create/")
    City createCity(@RequestBody CityDto cityDto) {
        return cityService.createCity(cityDto);
    }

    @GetMapping("/city/all/")
    public List<CityDto> All() {
        return cityService.findAllCities();
    }

    @PutMapping("/city/editbyid/{id}")
    City editCity(@RequestBody CityDto newCityDto, @PathVariable Long id) {
        return cityRepository.findById(id)
                .map(city -> {
                    city.setCityName(newCityDto.getCityName());
                    return cityRepository.save(city);
                })
                .orElseGet(() -> {
                    newCityDto.setId(id);
                    City entity = cityMapper.toEntity(newCityDto);
                    return cityRepository.save(entity);
                });
    }

    @DeleteMapping("/city/deletebyid/{id}")
    String deleteCityById(@PathVariable Long id) {
        cityService.deleteCityById(id);
        return "City with ID " + id + " is deleted";
    }
}
