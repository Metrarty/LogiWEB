package com.metrarty.LogiWEB.boundary.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class DistanceDto {
    private Long id;
    @NotNull
    private CityDto city1;
    @NotNull
    private CityDto city2;
    @Min(value = 1, message = "Distance should be no less than 1km.")
    @Max(value = 10000, message = "Distance should be no more than 10000km.")
    private Long distance;
}
