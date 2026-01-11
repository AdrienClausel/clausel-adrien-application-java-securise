package com.nnk.springboot.dtos;

public record CurvePointListDto(
        Integer Id,
        Integer CurveId,
        double term,
        double value
) {
}
