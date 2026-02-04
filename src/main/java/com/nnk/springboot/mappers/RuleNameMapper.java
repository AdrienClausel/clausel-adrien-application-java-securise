package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dtos.RatingDto;
import com.nnk.springboot.dtos.RuleNameDto;

import java.util.List;
import java.util.stream.Collectors;

public final class RuleNameMapper {
    private RuleNameMapper(){}

    public static RuleName toEntity(RuleNameDto dto){
        RuleName ruleName = new RuleName();
        ruleName.setName(dto.name());
        ruleName.setDescription(dto.description());
        ruleName.setJson(dto.json());
        ruleName.setTemplate(dto.template());
        ruleName.setSqlStr(dto.sqlStr());
        ruleName.setSqlPart(dto.sqlPart());
        return ruleName;
    }

    public static RuleNameDto toDto(RuleName ruleName){
        return new RuleNameDto(
                ruleName.getId(),
                ruleName.getName(),
                ruleName.getDescription(),
                ruleName.getJson(),
                ruleName.getTemplate(),
                ruleName.getSqlStr(),
                ruleName.getSqlPart()
        );
    }

    public static List<RuleNameDto> toDtoList(List<RuleName> ruleNames){
        return ruleNames.stream()
                .map(RuleNameMapper::toDto)
                .collect(Collectors.toList());
    }

}

