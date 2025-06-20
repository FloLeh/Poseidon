package com.openclassrooms.poseidon.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "curvepoint")
@NoArgsConstructor
public class CurvePoint implements DomainEntity<CurvePoint> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    Integer curveId;

    Timestamp asOfDate;

    @NotBlank
    Double term;

    @NotBlank
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
