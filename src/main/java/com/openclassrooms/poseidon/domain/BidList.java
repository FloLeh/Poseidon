package com.openclassrooms.poseidon.domain;


import jakarta.persistence.*;
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

    String account;

    String type;

    Double bidQuantity;

    Double askQuantity;

    Double bid;

    Double ask;

    String benchmark;

    Timestamp bidListDate;

    String commentary;

    String security;

    String status;

    String trader;

    String book;

    String creationName;

    Timestamp creationDate = Timestamp.valueOf(LocalDateTime.now());

    String revisionName;

    Timestamp revisionDate;

    String dealName;

    String dealType;

    String sourceListId;

    String side;

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
