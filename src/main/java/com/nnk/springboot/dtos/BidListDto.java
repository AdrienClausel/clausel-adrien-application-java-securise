package com.nnk.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BidListDto(
        Integer bidListId,
        @NotBlank(message =  "account is mandatory")
        @Size(max=30)
        String account,
        @NotBlank(message =  "type is mandatory")
        @Size(max=30)
        String type,
        double bidQuantity
) {

}
