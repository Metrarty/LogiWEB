package com.metrarty.LogiWEB.repository.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Data
@Entity
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Max(value = 25000, message = "Capacity should be no more than 25000 kg")
    private Long capacity;
    @OneToOne
    private City location;
    @Column
    @Max(value = 5000, message = "Distance should be no more than 5000km")
    private Long distancePerDay;
}
