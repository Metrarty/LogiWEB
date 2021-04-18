package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.repository.DistanceRepository;
import com.metrarty.LogiWEB.repository.entity.Distance;
import com.metrarty.LogiWEB.service.mapper.DistanceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2

public class DistancePreparationService {

    private final DistanceRepository distanceRepository;
    private final DistanceMapper distanceMapper;

    /**
     * Finds all exist distances.
     * @return List of distances DTO
     */
    public List<DistanceDto> prepareAllDistances() {
        List<Distance> entities = distanceRepository.findAll();
        List<DistanceDto> result = new ArrayList<>();
        for (Distance entity : entities) {
            DistanceDto distanceDto = distanceMapper.toDto(entity);
            result.add(distanceDto);
        }
        return result;
    }

    /**
     * Prepare list of suitable distances that includes city of order.
     * @param cityOrder order city DTO
     * @return list of distances
     */
    public List<DistanceDto> prepareSuitableDistances(CityDto cityOrder) {
        List<DistanceDto> allDistances = prepareAllDistances();
        List<DistanceDto> distanceSuitable = new ArrayList<>();
        for(DistanceDto distance : allDistances) {
            if (distance.getCity1().equals(cityOrder) || distance.getCity2().equals(cityOrder)) {
                distanceSuitable.add(distance);
            }
        }
        return distanceSuitable;
    }

}
