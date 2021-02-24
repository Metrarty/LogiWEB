package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * City controller.
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class CityController {
    private final CityService cityService;

    /**
     * Creates new city.
     * @param cityDto city DTO
     * @return created city
     */
    @PostMapping("/city/create/")
    public CityDto createCity(@RequestBody CityDto cityDto) {
        log.info("CityController.createCity was called with {}", cityDto);
        return cityService.createCity(cityDto);
    }

    /**
     * Receives all cities DTO.
     * @return List of all cities DTO
     */
    @GetMapping("/city/all/")
    public List<CityDto> all() {
        log.info("CityController.all was called");
        return cityService.findAllCities();
    }

    /**
     * Edits exist city DTO, selected by ID.
     * @param cityDto city DTO
     * @param id ID of city that should be edited
     * @return edited city DTO
     */
    @PutMapping("/city/editbyid/{id}")
    public CityDto editCity(@RequestBody CityDto cityDto, @PathVariable Long id) {
        log.info("CityController.editCity was called with {} {}", cityDto, id);
        return cityService.editCity(cityDto, id);
    }

    /**
     * Deletes exist city, selected by ID.
     * @param id city ID
     * @return message with ID of deleted city
     */
    @DeleteMapping("/city/deletebyid/{id}")
    public String deleteCityById(@PathVariable Long id) {
        log.info("CityController.deleteCityById was called with {}", id);
        cityService.deleteCityById(id);
        return "City with ID " + id + " is deleted";
    }
}
