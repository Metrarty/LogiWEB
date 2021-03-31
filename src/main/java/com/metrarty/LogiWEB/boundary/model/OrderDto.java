package com.metrarty.LogiWEB.boundary.model;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Data
public class OrderDto {
    private Long id;
    private CargoDto cargo;
    private CityDto destination;
    private Instant deliveryDate;
}
