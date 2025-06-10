package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "trade")
public class Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tradeId;
}
