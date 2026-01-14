package com.nnk.springboot.dtos;

public record TradeDto(
        String account,
        String type,
        Double buyQuantity
) {
}
