package com.metrarty.LogiWEB.boundary.model;

import com.metrarty.LogiWEB.repository.entity.City;
import lombok.Data;

@Data
public class DistanceDto {
    private Long id;
    private City city1;
    private City city2;
    private Long distance;
}
