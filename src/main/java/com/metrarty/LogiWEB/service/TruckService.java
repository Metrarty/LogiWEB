package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.repository.TruckRepository;
import com.metrarty.LogiWEB.repository.entity.Truck;
import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import com.metrarty.LogiWEB.service.mapper.TruckMapper;
import com.metrarty.LogiWEB.service.validator.TruckValidator;
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
    private final TruckValidator truckValidator;
    private final TruckSelectionService truckSelectionService;

    /**
     * Creates truck and saves into repository.
     * @param truckDto truck DTO
     * @return truck
     */
    public Truck createTruck(@NonNull TruckDto truckDto) {
        log.info("TruckService.createTruck was called with {}", truckDto);
        truckValidator.checkCapacitySize(truckDto.getCapacity());
        truckValidator.checkDistancePerDay(truckDto.getDistancePerDay());
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
        truckValidator.checkCapacitySize(truckDto.getCapacity());
        truckValidator.checkDistancePerDay(truckDto.getDistancePerDay());
        Truck editedTruck = truckMapper.toEntity(truckDto);
        Truck originalTruck = findOneTruckById(id);
        editedTruck.setId(originalTruck.getId());
        Truck savedTruck = truckRepository.save(editedTruck);
        return truckMapper.toDto(savedTruck);
    }

    /**
     * Deletes truck, selected by id.
     * @param id truck ID
     */
    public void deleteTruckById(@NonNull Long id) {
        log.info("TruckService.deleteTruckById was called with {}", id);
        truckRepository.deleteById(id);
    }

    /**
     * Find suitable truck that is in the order city, if absent - finds from nearest city.
     * @param id order city DTO id
     * @param size order size
     * @return truck DTO
     */
    public TruckDto chooseTruckToDeliver(Long id, Long size) {
        return truckSelectionService.chooseTruckToDeliver(id, size);
    }

    /**
     * Changes truck status.
     * @param truckId truck ID
     * @param status truck status
     * @return truck DTO with changed status
     */
    public TruckDto changeTruckStatus(Long truckId, String status) {
        Truck truck = findOneTruckById(truckId);
        truck.setTruckStatus(String.valueOf(status));
        Truck saved = truckRepository.save(truck);
        return truckMapper.toDto(saved);
    }

    /**
     * Finds one truck from repository by ID, if not found throws (@link EntityNotFoundException)
     * @param id truck ID
     * @return truck
     */
    public Truck findOneTruckById(Long id) {
        return truckRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Truck with ID " + id + " is not found"));
    }
}
