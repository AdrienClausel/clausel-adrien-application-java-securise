package com.nnk.springboot.dtos;

public record RatingDto(
       Integer id,
       String moodysRating,
       String sandPRating,
       String fitchRating,
       Integer orderNumber
) {
}
