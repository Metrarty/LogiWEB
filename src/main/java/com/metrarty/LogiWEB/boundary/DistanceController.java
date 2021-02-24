package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.repository.entity.Distance;
import com.metrarty.LogiWEB.service.DistanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class DistanceController {
    private final DistanceService distanceService;

    @PostMapping("/distance/create/")
    public Distance createDistance(DistanceDto distanceDto) {
        log.info("DistanceController.createDistance was called with {}", distanceDto);
        return distanceService.createDistance(distanceDto);
    }

    @GetMapping("/distance/all/")
    public List<DistanceDto> all() {
        log.info("DistanceController.all was called");
        return distanceService.findAllDistances();
    }

    @PutMapping("/distance/editbyid/{id}/")
    public DistanceDto editDistance(@RequestBody DistanceDto distanceDto, @PathVariable Long id) {
        log.info("DistanceController.editDistance was called with {} {}", distanceDto, id);
        return distanceService.editDistance(distanceDto, id);
    }

    @DeleteMapping("/distance/deletebyid/{id}/")
    public String deleteDistanceById(@PathVariable Long id) {
        log.info("DistanceController.deleteDistanceById was called with {}", id);
        distanceService.deleteDistanceById(id);
        return "Distance with ID " + id + " is deleted";
    }
}
