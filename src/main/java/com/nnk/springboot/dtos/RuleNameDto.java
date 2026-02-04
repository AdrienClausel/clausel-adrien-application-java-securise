package com.nnk.springboot.dtos;

import jakarta.validation.constraints.NotBlank;

public record RuleNameDto(
    Integer id,
    @NotBlank(message =  "name is mandatory")
    String name,
    @NotBlank(message =  "description is mandatory")
    String description,
    @NotBlank(message =  "json is mandatory")
    String json,
    @NotBlank(message =  "template is mandatory")
    String template,
    @NotBlank(message =  "sqlStr is mandatory")
    String sqlStr,
    @NotBlank(message =  "sqlPart is mandatory")
    String sqlPart
) {
}
