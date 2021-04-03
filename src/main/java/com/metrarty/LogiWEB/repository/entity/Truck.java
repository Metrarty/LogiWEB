package com.metrarty.LogiWEB.repository.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Data
@Entity
public class Truck {

    public enum Status {
        FREE, ASSIGNED, BROKEN;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long capacity;
    @OneToOne
    private City location;
    @Column
    private Long distancePerDay;
    @Enumerated(EnumType.STRING)
    private Status status;
}
