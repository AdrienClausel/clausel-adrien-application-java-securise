package com.nnk.springboot.services.curvePoint;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurvePointService implements ICurvePointService{

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Override
    public void add(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
    }

    @Override
    public void update(CurvePoint curvePointUpdate, Integer id) {
        var optionnalCurvePoint = curvePointRepository.findById(id);
        if (optionnalCurvePoint.isPresent()){
            var curvePoint = optionnalCurvePoint.get();
            curvePoint.setCurveId(curvePointUpdate.getCurveId());
            curvePoint.setTerm(curvePointUpdate.getTerm());
            curvePoint.setValue(curvePointUpdate.getValue());

            curvePointRepository.save(curvePoint);
        }
    }

    @Override
    public void delete(Integer id) {
        curvePointRepository.deleteById(id);
    }

    @Override
    public List<CurvePoint> getAll() {
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint getById(Integer id) {
        return curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CurvePoint not found: " + id));
    }
}
