package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.repository.entity.Distance;
import com.metrarty.LogiWEB.service.DistanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Distance controller
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class DistanceController {
    private final DistanceService distanceService;

    /**
     * Creates distance between two cities.
     * @param distanceDto distance DTO.
     * @return created distance
     */
    @PostMapping("/distance/create/")
    public Distance createDistance(@RequestBody DistanceDto distanceDto) {
        log.info("DistanceController.createDistance was called with {}", distanceDto);
        return distanceService.createDistance(distanceDto);
    }

    /**
     * Receives all distances DTO.
     * @return list of all distances DTO
     */
    @GetMapping("/distance/all/")
    public List<DistanceDto> findAll() {
        log.info("DistanceController.all was called");
        return distanceService.findAllDistances();
    }

    /**
     * Edits exist distance DTO, selected by ID.
     * @param distanceDto distance DTO
     * @param id ID of distance that should be edited
     * @return edited distance DTO
     */
    @PutMapping("/distance/editbyid/{id}/")
    public DistanceDto editDistance(@RequestBody DistanceDto distanceDto, @PathVariable Long id) {
        log.info("DistanceController.editDistance was called with {} {}", distanceDto, id);
        return distanceService.editDistance(distanceDto, id);
    }

    /**
     * Deletes exist distance, selected by ID.
     * @param id distance ID
     * @return message with ID of deleted distance
     */
    @DeleteMapping("/distance/deletebyid/{id}/")
    public String deleteDistanceById(@PathVariable Long id) {
        log.info("DistanceController.deleteDistanceById was called with {}", id);
        distanceService.deleteDistanceById(id);
        return "Distance with ID " + id + " is deleted";
    }
}
