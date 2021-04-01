package com.metrarty.LogiWEB.boundary.model;

import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class OrderDto {
    private Long id;
    private CargoDto cargo;
    private CityDto destination;
    @NotNull
    @FutureOrPresent(message = "Delivery date should be present or future.")
    private Instant deliveryDate;
}
