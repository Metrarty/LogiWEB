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
    private City destination;
    @Column
    private Instant approximatelyDeliveryDate;
    @Column(updatable = false)
    @NotNull
    private Instant createdAt;
    @Column
    private Instant changedAt;
    @Column
    private Instant deliveredAt;
    @Column
    private Instant completedAt;
    @OneToOne
    private Truck assignedTruck;

}
