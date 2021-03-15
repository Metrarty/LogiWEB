package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.service.TruckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Truck controller.
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class TruckController {
    private final TruckService truckService;

    /**
     * Creates new truck.
     * @param truckDto truck DTO
     * @return created truck
     */
    @PostMapping("/truck/create/")
    public Truck createTruck(@RequestBody TruckDto truckDto) {
        log.info("TruckController.createTruck was called with {}", truckDto);
        return truckService.createTruck(truckDto);
    }

    /**
     * Receives all trucks DTO.
     * @return list of all trucks DTO
     */
    @GetMapping("/truck/all/")
    public List<TruckDto> findAll() {
        log.info("TruckController.findAll was called");
        return truckService.findAllTrucks();
    }

    /**
     * Edits exist truck DTO, selected by ID.
     * @param truckDto truck DTO
     * @param id ID of truck that should be edited
     * @return edited truck DTO
     */
    @PutMapping("/truck/editbyid/{id}/")
    public TruckDto editTruck(@RequestBody TruckDto truckDto, @PathVariable Long id) {
        log.info("truckController.editTruck was called with {} {}", truckDto, id);
        return truckService.editTruck(truckDto, id);
    }

    /**
     * Deletes exist truck, selected by ID.
     * @param id truck ID.
     * @return message with ID of deleted truck
     */
    @DeleteMapping("/truck/deletebyid/{id}/")
    public String deleteTruckById(@PathVariable Long id) {
        log.info("TruckController.deleteTruckById was called with {}", id);
        truckService.deleteTruckById(id);
        return "Truck with ID " + id + " is deleted";
    }

    /**
     * Creates list of trucks that are in the order city, if absent - adds trucks from nearest cities.
     * @param id order city DTO ID
     * @param size order size
     * @return list trucks DTO
     */
    @GetMapping("/truck/choose/{id}/{size}/")
    public List<TruckDto> chooseTruckToDeliver(@PathVariable Long id, @PathVariable Long size) {
        log.info("TruckController.chooseTruckToDeliver was called with {} {}", id, size);
        return truckService.chooseTruckToDeliver(id, size);
    }
}
