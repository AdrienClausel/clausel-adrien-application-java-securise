package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "rulename")
public class RuleName {
    // TODO: Map columns in data table RULENAME with corresponding java fields
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;

    @Size(max=125)
    private String name;

    @Size(max=125)
    private String description;

    @Size(max=125)
    private String json;

    @Size(max=512)
    private String template;

    @Size(max=125)
    private String sqlStr;

    @Size(max=125)
    private String sqlPart;

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
}
