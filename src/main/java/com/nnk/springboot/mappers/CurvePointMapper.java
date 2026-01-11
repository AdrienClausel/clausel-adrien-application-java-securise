package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dtos.CurvePointDto;

import java.util.List;
import java.util.stream.Collectors;

public final class CurvePointMapper {

    private CurvePointMapper(){}

    public static CurvePoint toEntity(CurvePointDto dto){
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(dto.curveId());
        curvePoint.setTerm(dto.term());
        curvePoint.setValue(dto.value());
        return curvePoint;
    }

    public static CurvePointDto toDto(CurvePoint curvePoint){
        return new CurvePointDto(
                curvePoint.getId(),
                curvePoint.getCurveId(),
                curvePoint.getTerm(),
                curvePoint.getValue()
        );
    }

    public static List<CurvePointDto> toDoList(List<CurvePoint> curvePoints){
        return curvePoints.stream()
                .map(CurvePointMapper::toDto)
                .collect(Collectors.toList());
    }
}
