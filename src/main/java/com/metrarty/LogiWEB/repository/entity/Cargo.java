package com.metrarty.LogiWEB.repository.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long size;
    @Column(updatable = false)
    @NotNull
    private Instant createdAt;
    @Column
    private Instant changedAt;
}
