package com.metrarty.LogiWEB.boundary.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CityDto {
    private Long id;
    @NotNull(message = "City name cannot be null")
    @Size(min = 10, max = 200, message = "City name must be between 2 and 100 characters")
    private String cityName;
}
