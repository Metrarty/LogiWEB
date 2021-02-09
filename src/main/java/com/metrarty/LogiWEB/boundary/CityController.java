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
    City editCity(@RequestBody City newCity, @PathVariable Long id) {
        return cityRepository.findById(id)
                .map(city -> {
                    city.setCityName(city.getCityName());
                    return cityRepository.save(city);
                })
                .orElseGet(() -> {
                    newCity.setId(id);
                    return cityRepository.save(newCity);
                });
    }
}
