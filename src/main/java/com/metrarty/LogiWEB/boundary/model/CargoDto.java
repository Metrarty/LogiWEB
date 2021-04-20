package com.metrarty.LogiWEB.boundary.model;

import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Instant;

@Data
public class CargoDto {
    private Long id;
    @Min(value = 1, message = "Size should not be less than 1")
    @Max(value = 25000, message = "Size should not be more than 25000")
    private Long size;
    @FutureOrPresent(message = "Date should be present or future.")
    private Instant deliveredAt;
}
