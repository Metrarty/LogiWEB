package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.repository.CityRepository;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.service.exception.DistanceIsInvalidException;
import org.springframework.stereotype.Component;

@Component
public class DistanceValidator {

    private DistanceValidator distanceValidator;

    public void apply(Long distance) {
        if (isDistanceLessOrEqualNull(distance)) {
            throw new DistanceIsInvalidException("Distance value is invalid");
        }
    }
    private boolean isDistanceLessOrEqualNull(Long distance) {
        return distance <= 0;
    }
}
