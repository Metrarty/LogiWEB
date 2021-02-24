package com.metrarty.LogiWEB.repository.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Distance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @OneToOne
    private City city1;
    @Column
    @OneToOne
    private City city2;
    @Column
    private Long distance;
}
