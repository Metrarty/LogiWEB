package com.metrarty.LogiWEB.boundary.model;

import com.metrarty.LogiWEB.repository.entity.Truck;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class TruckDto {
    private Long id;
    @Min(value = 1, message = "Capacity should be no less than 1kg.")
    @Max(value = 25000, message = "Capacity should be no more than 25000kg.")
    private Long capacity;
    @NotNull
    private CityDto location;
    @Min(value = 1, message = "Distance should be no less than 5000km.")
    @Max(value = 5000, message = "Distance should be no more than 5000km.")
    private Long distancePerDay;
    private Truck.Status status;
}
