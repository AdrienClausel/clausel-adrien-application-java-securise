package com.nnk.springboot.dtos;

public record TradeDto(
        Integer id,
        String account,
        String type,
        Double buyQuantity
) {
}
