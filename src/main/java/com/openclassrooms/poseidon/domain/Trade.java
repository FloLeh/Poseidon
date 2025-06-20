package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "trade")
@NoArgsConstructor
public class Trade implements DomainEntity<Trade> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tradeId;

    @NotBlank
    String account;

    @NotBlank
    String type;

    @NotBlank
    Double buyQuantity;

    Double sellQuantity;

    Double buyPrice;

    Double sellPrice;

    String benchmark;

    Timestamp tradeDate;

    String security;

    String status;

    String trader;

    String book;

    String creationName;

    Timestamp creationDate;

    String revisionName;

    Timestamp revisionDate;

    String dealName;

    String dealType;

    String sourceListId;

    String side;

    public Integer getId() {
        return tradeId;
    }

    public Trade(String account, String type, Double buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }

    @Override
    public void update(Trade trade) {
        account = trade.getAccount();
        type = trade.getType();
        buyQuantity = trade.getBuyQuantity();
    }
}
