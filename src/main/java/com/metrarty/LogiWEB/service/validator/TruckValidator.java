package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.repository.TruckRepository;
import com.metrarty.LogiWEB.service.exception.CargoSizeIsInvalidException;
import com.metrarty.LogiWEB.service.exception.TruckNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class TruckValidator {
    private final TruckRepository truckRepository;

    public TruckValidator(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    /**
     * Throws (@link CargoSizeIsInvalidException) if capacity less or equal null.
     * @param capacity
     */
    public void checkCapacitySize(Long capacity) {
        if (isCapacityLessOrEqualNull(capacity)) {
            throw new CargoSizeIsInvalidException("Capacity size is invalid");
        }
    }


    /**
     * Throws (@link CargoSizeIsInvalidException) if capacity less or equal null.
     * @param distancePerDay
     */
    public void checkDistancePerDay(Long distancePerDay) {
        if (isDistancePerDayLessOrEqualNull(distancePerDay)) {
            throw new CargoSizeIsInvalidException("Distance per day size is invalid");
        }
    }

    /**
     *
     * @param id
     */
    public void checkTruckExistence(Long id) {
        if(!isTruckExists(id)) {
            throw new TruckNotFoundException("Truck with ID " + id + " is not found.");
        }
    }

    private boolean isCapacityLessOrEqualNull(Long size) {
        return size <= 0;
    }

    private boolean isDistancePerDayLessOrEqualNull(Long size) {
        return size <= 0;
    }

    private boolean isTruckExists(Long id) {
        return (truckRepository.existsById(id));
    }
}
