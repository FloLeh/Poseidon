package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "rulename")
public class RuleName  implements DomainEntity {
    // TODO: Map columns in data table RULENAME with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
}
