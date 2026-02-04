package com.nnk.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TradeDto(
        Integer id,
        @NotBlank(message =  "account is mandatory")
        String account,
        @NotBlank(message =  "type is mandatory")
        String type,
        @NotNull(message =  "buyQuantity is mandatory")
        Double buyQuantity
) {
}
