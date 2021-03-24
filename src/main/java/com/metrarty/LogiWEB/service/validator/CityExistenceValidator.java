package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.repository.CityRepository;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.service.exception.CityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CityExistenceValidator {

    private CityRepository cityRepository;

    public void apply(Long id) {

    }

    private boolean isCityExist(Long id) {
        return cityRepository.existsById(id);
    }
}
