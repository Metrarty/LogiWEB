package com.metrarty.LogiWEB.repository.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

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
    private Instant deliveryDate;
    @Column(updatable = false)
    @NotNull
    private Instant createdAt;
    @Column
    private Instant changedAt;
}
