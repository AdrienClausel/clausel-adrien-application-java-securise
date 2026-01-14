package com.nnk.springboot.dtos;

public record RuleNameDto(
    Integer id,
    String name,
    String description,
    String json,
    String template,
    String sqlStr,
    String sqlPart
) {
}
