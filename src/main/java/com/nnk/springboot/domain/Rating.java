package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "rating")
public class Rating {
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

    public Rating(String moodysRating, String sandPRating, String fitchRating, int orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
