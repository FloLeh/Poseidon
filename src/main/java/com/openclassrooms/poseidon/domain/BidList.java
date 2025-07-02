package com.openclassrooms.poseidon.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "BidList")
@NoArgsConstructor
public class BidList implements DomainEntity<BidList> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BidListId")
    private Integer BidListId;

    @NotBlank
    private String account;

    @NotBlank
    private String type;

    @Positive
    private Double bidQuantity;

    private Double askQuantity;

    private Double bid;

    private Double ask;

    private String benchmark;

    private Timestamp bidListDate;

    private String commentary;

    private String security;

    private String status;

    private String trader;

    private String book;

    private String creationName;

    private Timestamp creationDate = Timestamp.valueOf(LocalDateTime.now());

    private String revisionName;

    private Timestamp revisionDate;

    private String dealName;

    private String dealType;

    private String sourceListId;

    private String side;

    public Integer getId() {
        return BidListId;
    }

    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public void update(BidList bidList) {
        account = bidList.getAccount();
        type = bidList.getType();
        bidQuantity = bidList.getBidQuantity();
        revisionDate = Timestamp.valueOf(LocalDateTime.now());
    }
}
