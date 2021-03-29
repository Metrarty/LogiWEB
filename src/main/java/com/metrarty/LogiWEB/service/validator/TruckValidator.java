package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.repository.TruckRepository;
import com.metrarty.LogiWEB.service.exception.ItemNotFoundException;
import com.metrarty.LogiWEB.service.exception.ValueIsInvalidException;
import org.springframework.stereotype.Component;

@Component
public class TruckValidator {
    private final TruckRepository truckRepository;

    public TruckValidator(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    /**
     * Throws (@link ValueIsInvalidException) if capacity less or equal null.
     * @param capacity
     */
    public void checkCapacitySize(Long capacity) {
        if (isCapacityLessOrEqualNull(capacity)) {
            throw new ValueIsInvalidException("Capacity size is invalid");
        }
    }


    /**
     * Throws (@link ValueIsInvalidException) if distance per day less or equal null.
     * @param distancePerDay
     */
    public void checkDistancePerDay(Long distancePerDay) {
        if (isDistancePerDayLessOrEqualNull(distancePerDay)) {
            throw new ValueIsInvalidException("Distance per day size is invalid");
        }
    }

    /**
     * Throws (@link ItemNotFoundException) if distance per day less or equal null.
     * @param id
     */
    public void checkTruckExistence(Long id) {
        if(!isTruckExists(id)) {
            throw new ItemNotFoundException("Truck with ID " + id + " is not found.");
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
