package com.metrarty.LogiWEB.boundary.model;

import com.metrarty.LogiWEB.repository.entity.OrderStatus;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class OrderDto {
    private Long id;
    @NotNull
    private CargoDto cargo;
    @NotNull
    private CityDto destination;
    private TruckDto assignedTruck;
    private Integer deliveryWorkingDays;
    @NotNull
    private OrderStatus orderStatus;
    @FutureOrPresent(message = "Date should be present or future.")
    private Instant createdAt;
    @FutureOrPresent(message = "Date should be present or future.")
    private Instant changedAt;
    @FutureOrPresent(message = "Date should be present or future.")
    private Instant deliveredAt;
    @FutureOrPresent(message = "Date should be present or future.")
    private Instant completedAt;
}
