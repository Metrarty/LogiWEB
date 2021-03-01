package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.repository.entity.Distance;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.service.TruckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class TruckController {
    private final TruckService truckService;

    @PostMapping("/truck/create/")
    public Truck createTruck(@RequestBody TruckDto truckDto) {
        log.info("TruckController.createTruck was called with {}", truckDto);
        return truckService.createTruck(truckDto);
    }

    @GetMapping("/truck/all/")
    public List<TruckDto> findAll() {
        log.info("TruckController.findAll was called");
        return truckService.findAllTrucks();
    }

    @PutMapping("/truck/editbyid/{id}/")
    public TruckDto editTruck(@RequestBody TruckDto truckDto, @PathVariable Long id) {
        log.info("truckController.editTruck was called with {} {}", truckDto, id);
        return truckService.editTruck(truckDto, id);
    }

    @DeleteMapping("/truck/deletebyid/{id}/")
    public String deleteTruckById(@PathVariable Long id) {
        log.info("TruckController.deleteTruckById was called with {}", id);
        truckService.deleteTruckById(id);
        return "Truck with ID " + id + " is deleted";
    }
}
