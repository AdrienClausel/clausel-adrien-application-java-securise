package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dtos.RatingDto;
import java.util.List;
import java.util.stream.Collectors;

public final class RatingMapper {
    private RatingMapper(){}

    public static Rating toEntity(RatingDto dto){
        Rating rating = new Rating();
        rating.setMoodysRating(dto.moodysRating());
        rating.setSandPRating(dto.sandPRating());
        rating.setFitchRating(dto.fitchRating());
        rating.setOrderNumber(dto.orderNumber());
        return rating;
    }

    public static RatingDto toDto(Rating rating){
        return new RatingDto(
                rating.getId(),
                rating.getMoodysRating(),
                rating.getSandPRating(),
                rating.getFitchRating(),
                rating.getOrderNumber()
        );
    }

    public static List<RatingDto> toDtoList(List<Rating> ratings){
        return ratings.stream()
                .map(RatingMapper::toDto)
                .collect(Collectors.toList());
    }

}
