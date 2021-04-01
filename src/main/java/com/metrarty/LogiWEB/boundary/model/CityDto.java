package com.metrarty.LogiWEB.boundary.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CityDto {
    private Long id;
    @NotEmpty(message = "City name cannot be empty.")
    @Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters.")
    private String cityName;
}
