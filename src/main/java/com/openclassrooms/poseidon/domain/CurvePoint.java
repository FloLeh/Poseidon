package com.openclassrooms.poseidon.domain;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "curvepoint")
public class CurvePoint  implements DomainEntity {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
}
