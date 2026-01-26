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
@Table(name = "bidlist")
public class BidList {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="BidListId")
    private Integer id;

    @NotBlank(message =  "account is mandatory")
    @Size(max=30)
    private String account;

    @NotBlank(message =  "type is mandatory")
    @Size(max=30)
    private String type;

    private double bidQuantity;

    private double askQuantity;

    private double bid;

    private double ask;

    @Size(max=125)
    private String benchmark;

    private Instant bidListDate;

    @Size(max=125)
    private String commentary;

    @Size(max=125)
    private String security;

    @Size(max=10)
    private String status;

    @Size(max=125)
    private String trader;

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

    public BidList(String account, String type, double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}
