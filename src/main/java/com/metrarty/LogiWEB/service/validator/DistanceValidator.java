package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.repository.DistanceRepository;
import com.metrarty.LogiWEB.service.exception.ItemNotFoundException;
import com.metrarty.LogiWEB.service.exception.ValueIsInvalidException;
import org.springframework.stereotype.Component;

@Component
public class DistanceValidator {

    private final DistanceRepository distanceRepository;

    public DistanceValidator(DistanceRepository distanceRepository) {
        this.distanceRepository = distanceRepository;
    }

    /**
     * Throws (@link ValueIsInvalidException) if distance less or equal null.
     * @param distance distance
     */
    public void checkDistance(Long distance) {
        if (isDistanceLessOrEqualNull(distance)) {
            throw new ValueIsInvalidException("Distance value is invalid");
        }
    }

    /**
     * Throws (@link ItemNotFoundException) if distance with ID is not exists.
     * @param id distance ID
     */
    public void checkDistanceExistence(Long id) {
        if (!isDistanceExist(id)) {
            throw new ItemNotFoundException("Distance with ID " + id + " is not found");
        }
    }

    private boolean isDistanceLessOrEqualNull(Long distance) {
        return distance <= 0;
    }

    private boolean isDistanceExist(Long id) {
        return distanceRepository.existsById(id);
    }
}
