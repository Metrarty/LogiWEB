package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.service.exception.ValueIsInvalidException;
import org.springframework.stereotype.Component;

@Component
public class TruckValidator {

    /**
     * Throws (@link ValueIsInvalidException) if capacity less or equal zero.
     * @param capacity
     */
    public void checkCapacitySize(Long capacity) {
        if (isCapacityLessOrEqualZero(capacity)) {
            throw new ValueIsInvalidException("Capacity size is invalid");
        }
    }

    /**
     * Throws (@link ValueIsInvalidException) if distance per day less or equal zero.
     * @param distancePerDay
     */
    public void checkDistancePerDay(Long distancePerDay) {
        if (isDistancePerDayLessOrEqualZero(distancePerDay)) {
            throw new ValueIsInvalidException("Distance per day size is invalid");
        }
    }

    private boolean isCapacityLessOrEqualZero(Long size) {
        return size <= 0;
    }

    private boolean isDistancePerDayLessOrEqualZero(Long distancePerDay) {
        return distancePerDay <= 0;
    }

}
