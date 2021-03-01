package com.metrarty.LogiWEB.boundary.model;

import com.metrarty.LogiWEB.repository.entity.City;
import lombok.Data;

@Data
public class DistanceDto {
    private Long id;
    private CityDto city1;
    private CityDto city2;
    private Long distance;
}
