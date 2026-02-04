package com.nnk.springboot.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CurvePointDto(
        Integer id,
        @NotNull(message =  "must not be null")
        @Positive(message = "must be positive")
        Integer curveId,
        @NotNull(message =  "must not be null")
        Double term,
        @NotNull(message =  "must not be null")
        Double value
) {
}
