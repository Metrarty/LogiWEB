package com.metrarty.LogiWEB.service.validator;

import com.metrarty.LogiWEB.repository.CityRepository;
import com.metrarty.LogiWEB.service.exception.CityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CityValidator {

    private final CityRepository cityRepository;

    public CityValidator(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    /**
     * Throws (@link CityNotFoundException) if city with ID is not exists.
     * @param id city ID
     */
    public void checkCityExistence(Long id) {
        if (!isCityExist(id)) {
            throw new CityNotFoundException("City with ID " + id + " is not found");
        }
    }

    private boolean isCityExist(Long id) {
        return cityRepository.existsById(id);
    }
}
