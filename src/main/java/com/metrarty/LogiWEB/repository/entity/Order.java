package com.metrarty.LogiWEB.repository.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Cargo cargo;
    @OneToOne
    private City sourceCity;
    @OneToOne
    private City destination;
    @OneToOne
    private Truck assignedTruck;
    @Column
    private Integer deliveryWorkingDays;
    @Column
    private String orderStatus;
    @Column(updatable = false)
    @NotNull
    private Instant createdAt;
    @Column
    private Instant changedAt;
    @Column
    private Instant completedAt;
    @Column
    private Instant cancelledAt;
}
