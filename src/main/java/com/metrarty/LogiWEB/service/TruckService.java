package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.repository.CityRepository;
import com.metrarty.LogiWEB.repository.TruckRepository;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.repository.entity.Distance;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.service.exception.TruckNotFoundException;
import com.metrarty.LogiWEB.service.mapper.CityMapper;
import com.metrarty.LogiWEB.service.mapper.TruckMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.CookiePolicy;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Truck service.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class TruckService {

    private final TruckRepository truckRepository;
    private final TruckMapper truckMapper;
    private final DistanceService distanceService;
    private final CityService cityService;

    /**
     * Creates truck and saves into repository.
     * @param truckDto truck DTO
     * @return truck
     */
    public Truck createTruck(@NonNull TruckDto truckDto) {
        log.info("TruckService.createTruck was called with {}", truckDto);
        Truck entity = truckMapper.toEntity(truckDto);
        truckRepository.save(entity);
        return entity;
    }

    /**
     * Finds all exist trucks.
     * @return List of trucks DTO
     */
    public List<TruckDto> findAllTrucks() {
        log.info("TruckService.findAllTrucks was called");
        List<Truck> entities = truckRepository.findAll();
        List<TruckDto> result = new ArrayList<>();
        for (Truck entity : entities) {
            TruckDto truckDto = truckMapper.toDto(entity);
            result.add(truckDto);
        }
        return result;
    }

    /**
     * Edits truck with exact ID.
     * @param truckDto truck DTO
     * @param id truck ID
     * @return edited truck DTO
     */
    public TruckDto editTruck(@NonNull TruckDto truckDto, @NonNull Long id) {
        log.info("TruckService.editTruck was called with {}", id);
        Truck truck = truckMapper.toEntity(truckDto);
        Truck entity = truckRepository.findById(id)
                .orElseThrow(()-> new TruckNotFoundException("Truck with ID " + id + " is not found"));
        truck.setId(entity.getId());
        Truck saved = truckRepository.save(truck);
        return truckMapper.toDto(saved);
    }

    /**
     * Deletes truck, selected by id
     * @param id truck ID
     */
    public void deleteTruckById(@NonNull Long id) {
        log.info("TruckService.deleteTruckById was called with {}", id);
        truckRepository.deleteById(id);
    }

    public List<TruckDto> chooseTruckToDeliver(@NonNull Long id, @NonNull Long size) {

        // TODO metrarty 10.03.2021: перенести в city, добавить метод findById и exception handler
        CityDto cityOrder = cityService.;

        List<DistanceDto> distanceSuitable = prepareSuitableDistances(cityOrder);


        //Sort in ascending order
        distanceSuitable.sort(Comparator.comparingLong(DistanceDto::getDistance));

        //Create list of all available trucks
        List<TruckDto> allTrucks = findAllTrucks();

        //Create a map truck - city from the list of trucks that fit the capacity
        Map<TruckDto, CityDto> trucksSuitable = new HashMap();
        for (TruckDto truck : allTrucks) {
            Long value = truck.getCapacity();
            if (value >= size) {
                trucksSuitable.put(truck, truck.getLocation());
            }
        }

        //Add to the list cars that are already in the destination city
        List<TruckDto> result = trucksSuitable.entrySet()
                .stream()
                .filter(entry ->
                        entry.getValue().equals(cityOrder))
                .map(Map.Entry::getKey).collect(Collectors.toList());

        //If there are no such cars, looking in the nearest city
        if (result.isEmpty()) {
            for (int i = 0; i < distanceSuitable.size(); i++) {
                for (Map.Entry entry: trucksSuitable.entrySet()) {
                    if (entry.getValue().equals(distanceSuitable.get(i).getCity1()) ||
                            entry.getValue().equals(distanceSuitable.get(i).getCity2())) {
                        result.add((TruckDto) entry.getKey());
                    }
                }
            }
        }
        return result;
    }

    private List<DistanceDto> prepareSuitableDistances(CityDto cityOrder) {
        List<DistanceDto> allDistances = distanceService.findAllDistances();
        List<DistanceDto> distanceSuitable = new ArrayList<>();
        for(DistanceDto distance : allDistances) {
            if (distance.getCity1().equals(cityOrder) || distance.getCity2().equals(cityOrder)) {
                distanceSuitable.add(distance);
            }
        }
        return distanceSuitable;
    }
    // TODO metrarty 10.03.2021: разбить на маленькие методы
    // TODO metrarty 10.03.2021: посмотреть как в spring ограничить кол-во загружаемых данных из репозитория
}
