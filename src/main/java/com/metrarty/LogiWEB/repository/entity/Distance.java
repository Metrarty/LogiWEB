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
    private String city1;
    @Column
    private String city2;
    @Column
    private Long distance;
}
