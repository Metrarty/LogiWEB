package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.repository.CityRepository;
import com.metrarty.LogiWEB.service.exception.CityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CityExistenceValidator {

    private final CityRepository cityRepository;

    public CityExistenceValidator(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public void apply(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new CityNotFoundException("City with ID " + id + " is not found");
        }
    }

    private boolean isCityExist(Long id) {
        return cityRepository.existsById(id);
    }
}
