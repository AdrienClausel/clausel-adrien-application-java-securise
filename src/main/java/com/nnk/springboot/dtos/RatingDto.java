package com.nnk.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RatingDto(
       Integer id,
       @NotBlank(message =  "moodysRating is mandatory")
       String moodysRating,
       @NotBlank(message =  "sandPRating is mandatory")
       String sandPRating,
       @NotBlank(message =  "fitchRating is mandatory")
       String fitchRating,
       @NotNull(message =  "orderNumber is mandatory")
       Integer orderNumber
) {
}
