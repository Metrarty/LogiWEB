package com.metrarty.LogiWEB.repository.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Long capacity;
    @Column
    private City location;
    @Column
    private Long distancePerDay;
}
