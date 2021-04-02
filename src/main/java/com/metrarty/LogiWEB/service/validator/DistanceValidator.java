package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.service.exception.ValueIsInvalidException;
import org.springframework.stereotype.Component;

@Component
public class DistanceValidator {


    /**
     * Throws (@link ValueIsInvalidException) if distance less or equal null.
     * @param distance distance
     */
    public void checkDistance(Long distance) {
        if (isDistanceLessOrEqualZero(distance)) {
            throw new ValueIsInvalidException("Distance value is invalid");
        }
    }

    private boolean isDistanceLessOrEqualZero(Long distance) {
        return distance <= 0;
    }
}
