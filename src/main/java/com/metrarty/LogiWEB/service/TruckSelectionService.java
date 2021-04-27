package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.repository.TruckRepository;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.repository.entity.TruckStatus;
import com.metrarty.LogiWEB.service.mapper.TruckMapper;
import com.metrarty.LogiWEB.service.validator.CargoValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Truck selection service.
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class TruckSelectionService {

    private final CargoValidator cargoValidator;
    private final CityService cityService;
    private final DistanceService distanceService;
    private final TruckRepository truckRepository;
    private final TruckMapper truckMapper;

    /**
     * Find suitable truck that is in the order city, if absent - finds from nearest city.
     * @param id order city DTO id
     * @param size order size
     * @return truck DTO
     */
    public TruckDto chooseTruckToDeliver(@NonNull Long id, @NonNull Long size) {
        cargoValidator.checkCargo(size);

        CityDto cityOrder = cityService.findCityById(id);

        List<TruckDto> allTrucksWithFreeStatus = findTrucksWithFreeStatus();
        Map<TruckDto, CityDto> trucksSuitable = prepareSuitableTrucksMap(allTrucksWithFreeStatus, size);
        List<TruckDto> result = checkIfTruckIsInCityOrder(trucksSuitable, cityOrder);

        if (result.isEmpty()) {
            List<DistanceDto> distanceSuitable = distanceService.prepareSuitableDistances(cityOrder);
            distanceSuitable.sort(Comparator.comparingLong(DistanceDto::getDistance));
            addToResultNearestTrucks(result, distanceSuitable, trucksSuitable);
        }
        return result.get(0);
    }

    private Map<TruckDto, CityDto> prepareSuitableTrucksMap(List<TruckDto> allTrucks, Long size) {
        Map<TruckDto, CityDto> trucksSuitable = new HashMap<>();
        for (TruckDto truck : allTrucks)
            if (truck.getCapacity() >= size) {
                trucksSuitable.put(truck, truck.getLocation());
            }
        return trucksSuitable;
    }

    private List<TruckDto> checkIfTruckIsInCityOrder(Map<TruckDto, CityDto> trucksSuitable, CityDto cityOrder) {
        List<TruckDto> trucks = new ArrayList<>();
        for (Map.Entry<TruckDto, CityDto> entry : trucksSuitable.entrySet()) {
            if (entry.getValue().equals(cityOrder)) {
                TruckDto key = entry.getKey();
                trucks.add(key);
            }
        }
        return trucks;
    }

    private void addToResultNearestTrucks(List<TruckDto> result, List<DistanceDto> distanceSuitable, Map<TruckDto, CityDto> trucksSuitable) {
        for (DistanceDto distanceDto : distanceSuitable) {
            for (Map.Entry<TruckDto, CityDto> entry : trucksSuitable.entrySet()) {
                TruckDto key = entry.getKey();
                CityDto value = entry.getValue();
                if (value.equals(distanceDto.getCity1()) || value.equals(distanceDto.getCity2())) {
                    result.add(key);
                }
            }
        }
    }

    /**
     * Find all trucks with status FREE.
     * @return List of trucks DTO
     */
    public List<TruckDto> findTrucksWithFreeStatus() {
        log.info("TruckService.findTrucksWithFreeStatus was called");
        List<Truck> entities = truckRepository.findAllByTruckStatus(TruckStatus.FREE.name());
        List<TruckDto> result = new ArrayList<>();
        for (Truck entity : entities) {
            TruckDto truckDto = truckMapper.toDto(entity);
            result.add(truckDto);
        }
        return result;
    }
}
