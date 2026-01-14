package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Data
@Entity
@Table(name = "trade")
public class Trade {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "TradeId")
    private Integer id;

    @NotBlank(message =  "account is mandatory")
    @Size(max=30)
    private String account;

    @NotBlank(message =  "type is mandatory")
    @Size(max=30)
    private String type;

    private double buyQuantity;

    private double sellQuantity;

    private double buyPrice;

    private double sellPrice;

    private Instant tradeDate;

    @Size(max=125)
    private String security;

    @Size(max=10)
    private String status;

    @Size(max=125)
    private String trader;

    @Size(max=125)
    private String benchmark;

    @Size(max=125)
    private String book;

    @Size(max=125)
    private String creationName;

    private Instant creationDate;

    @Size(max=125)
    private String revisionName;

    private Instant revisionDate;

    @Size(max=125)
    private String dealName;

    @Size(max=125)
    private String dealType;

    @Size(max=125)
    private String sourceListId;

    @Size(max=125)
    private String side;

    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }
}
