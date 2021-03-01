package com.metrarty.LogiWEB.boundary.model;

import lombok.Data;

@Data
public class TruckDto {
    private Long id;
    private Long capacity;
    private CityDto location;
    private Long distancePerDay;
}
