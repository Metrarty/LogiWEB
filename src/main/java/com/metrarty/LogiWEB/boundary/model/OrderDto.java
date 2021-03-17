package com.metrarty.LogiWEB.boundary.model;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {
    private Long id;
    private CargoDto cargo;
    private CityDto destination;
    private Date deliveryDate;
}
