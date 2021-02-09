package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.repository.CityRepository;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CityController {
    private final CityRepository cityRepository;
    private final CityService cityService;

    @PostMapping("/city/create/")
    City createCity(@RequestBody CityDto cityDto) {
        return cityService.createCity(cityDto);
    }

    @GetMapping("/city/all/")
    public List<CityDto> All() {
        return cityService.findAllCities();
    }
}
