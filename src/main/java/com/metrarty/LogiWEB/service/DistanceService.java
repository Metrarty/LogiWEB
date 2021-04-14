package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.repository.DistanceRepository;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.repository.entity.Distance;
import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import com.metrarty.LogiWEB.service.mapper.DistanceMapper;
import com.metrarty.LogiWEB.service.validator.DistanceValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Distance service.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class DistanceService {
    private final DistanceRepository distanceRepository;
    private final DistanceMapper distanceMapper;
    private final DistanceValidator distanceValidator;

    /**
     * Creates distance and saves into repository.
     * @param distanceDto distance DTO
     * @return distance
     */
    public Distance createDistance(@NonNull DistanceDto distanceDto) {
        log.info("DistanceService.createDistance was called with {}", distanceDto);
        distanceValidator.checkDistance(distanceDto.getDistance());
        Distance entity = distanceMapper.toEntity(distanceDto);
        distanceRepository.save(entity);
        return entity;
    }

    /**
     * Finds all exist distances.
     * @return List of distances DTO
     */
    public List<DistanceDto> findAllDistances() {
        log.info("DistanceService.findAllDistances was called");
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
        List<DistanceDto> allDistances = findAllDistances();
        List<DistanceDto> distanceSuitable = new ArrayList<>();
        for(DistanceDto distance : allDistances) {
            if (distance.getCity1().equals(cityOrder) || distance.getCity2().equals(cityOrder)) {
                distanceSuitable.add(distance);
            }
        }
        return distanceSuitable;
    }

    /**
     * Edits distance with exact ID.
     * @param distanceDto distance DTO
     * @param id distance ID
     * @return edited distance DTO
     */
    public DistanceDto editDistance(@NonNull DistanceDto distanceDto, @NonNull Long id) {
        log.info("DistanceService.editDistance was called with {}", id);
        distanceValidator.checkDistance(distanceDto.getDistance());

        Distance distance = distanceMapper.toEntity(distanceDto);
        Distance entity = findOneDistanceById(id);

        distance.setId(entity.getId());
        Distance saved = distanceRepository.save(distance);
        return distanceMapper.toDto(saved);
    }

    /**
     * Deletes distance, selected by id.
     * @param id distance ID
     */
    public void deleteDistanceById(@NonNull Long id) {
        log.info("DistanceService.deleteDistanceById was called with {}", id);
        distanceRepository.deleteById(id);
    }

    private Distance findOneDistanceById(Long id) {
        return distanceRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Distance with ID " + id + " is not found"));
    }

    public Long distanceFromTruckToDestination(City orderDestination, City truckLocation) {
        List<DistanceDto> allDistances = findAllDistances();

        Long distanceFromTruckToDestination = 0L;
        for (DistanceDto distanceBetweenCities : allDistances) {
            if ((distanceBetweenCities.getCity1().getId().equals(orderDestination.getId())
                    && distanceBetweenCities.getCity2().getId().equals(truckLocation.getId()))) {
                distanceFromTruckToDestination = distanceBetweenCities.getDistance();
            }
            if ((distanceBetweenCities.getCity1().getId().equals(truckLocation.getId())
                    && distanceBetweenCities.getCity2().getId().equals(orderDestination.getId()))) {
                distanceFromTruckToDestination = distanceBetweenCities.getDistance();
            }
        }
        return (distanceFromTruckToDestination);
    }
}
