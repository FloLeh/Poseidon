package com.openclassrooms.poseidon.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Curvepoint")
@NoArgsConstructor
public class CurvePoint implements DomainEntity<CurvePoint> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    Integer curveId;

    Timestamp asOfDate;

    Double term;

    Double value;

    Timestamp creationDate = Timestamp.valueOf(LocalDateTime.now());

    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    @Override
    public void update(CurvePoint curvePoint) {
        curveId = curvePoint.curveId;
        term = curvePoint.term;
        value = curvePoint.value;
    }
}
