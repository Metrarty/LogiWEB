package com.metrarty.LogiWEB.boundary.model;

import lombok.Data;

import javax.validation.constraints.Max;

@Data
public class TruckDto {
    private Long id;
    @Max(value = 25000, message = "Capacity should be no more than 25000 kg")
    private Long capacity;
    private CityDto location;
    @Max(value = 5000, message = "Distance should be no more than 5000km")
    private Long distancePerDay;
}
