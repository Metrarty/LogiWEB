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

    @PostMapping("/distance/create/{idCity1}/{idCity2}/{distance}")
    public Distance createDistance(@PathVariable("idCity1") Long idCity1, @PathVariable("idCity2") Long idCity2, @PathVariable("distance") Long distance) {
        return distanceService.createDistance(idCity1, idCity2, distance);
    }

    @GetMapping("/distance/all")
    public List<DistanceDto> all() {
        return distanceService.findAllDistances();
    }


}
