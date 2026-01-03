package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;

    @Size(max=125)
    private String moodysRating;

    @Size(max=125)
    private String sandPRating;

    @Size(max=125)
    private String fitchRating;

    private Integer orderNumber;
}
