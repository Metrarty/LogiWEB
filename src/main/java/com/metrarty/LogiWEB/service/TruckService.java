package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.repository.TruckRepository;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.service.exception.TruckNotFoundException;
import com.metrarty.LogiWEB.service.mapper.TruckMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Truck service.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class TruckService {

    private final TruckRepository truckRepository;
    private final TruckMapper truckMapper;

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
}
