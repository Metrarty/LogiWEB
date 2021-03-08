package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.repository.TruckRepository;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.repository.entity.Distance;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.service.exception.TruckNotFoundException;
import com.metrarty.LogiWEB.service.mapper.TruckMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public TruckDto chooseTruckToDeliver(@NonNull CityDto city, Long size) {
        Long minimalDistance = 0L;
        //создаем список всех имеющихся грузовиков
        List<TruckDto> allTrucks = findAllTrucks();
        //создаем мапу грузовик-город из списка грузовиков, которые подходят по вместимости
        Map<TruckDto, CityDto> trucksSuitable = new HashMap();
        for (TruckDto truck : allTrucks) {
            Long value = truck.getCapacity();
            if (value >= size) {
                trucksSuitable.put(truck, truck.getLocation());
            }
        }

        //создаем список всех дистанций между городами, только если они содержат город заказа
        List<DistanceDto> allDistances = distanceService.findAllDistances();
        List<DistanceDto> distanceSuitable = new ArrayList<>();
        for(DistanceDto distance : allDistances) {
            if (distance.getCity1().equals(city) || distance.getCity2().equals(city)) {
                distanceSuitable.add(distance);
            }
        }
// TODO metrarty 08.03.2021: отсортировать все дистанции по возра
// TODO metrarty 08.03.2021: если нет грузовика в пункте назначения, найти следуюющий минимальный (из списка дистанций)

//        TruckDto result;
//        List<DistanceDto> sortedDistances = new ArrayList<>();
//        for (CityDto cityDto : trucksSuitable)
//        for (DistanceDto distanceDto : distanceSuitable) {
//            if (distanceDto.getDistance() > minimalDistance)
//        }



        return null;
    }
}
