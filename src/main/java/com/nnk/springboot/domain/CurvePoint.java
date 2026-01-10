package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Data
@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;

    private Integer CurveId;

    private Instant asOfDate;

    private double term;

    private double value;

    private Instant creationDate;

    public CurvePoint(int curveId, double term, double value) {
        this.CurveId = curveId;
        this.term = term;
        this.value = value;
    }
}
