package com.openclassrooms.poseidon.domain;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "bidlist")
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer BidListId;
}
