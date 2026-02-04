package com.nnk.springboot.services.curvePoint;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

public interface ICurvePointService {
    void add(CurvePoint curvePoint);
    void update(CurvePoint curvePoint, Integer id);
    void delete(Integer id);
    List<CurvePoint> getAll();
    CurvePoint getById(Integer id);
}
