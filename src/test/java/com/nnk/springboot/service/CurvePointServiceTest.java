package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.curvePoint.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    @InjectMocks
    private CurvePointService curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;

    private CurvePoint curvePoint;
    private CurvePoint curvePointUpdate;

    @BeforeEach
    void setUp(){
        curvePoint = new CurvePoint(1,2.2, 3.5);
        curvePoint.setId(1);

        curvePointUpdate = new CurvePoint(2,3.1,4);
    }

    @Test
    void add_shouldSave(){
        curvePointService.add(curvePoint);

        ArgumentCaptor<CurvePoint> argumentCaptor = ArgumentCaptor.forClass(CurvePoint.class);
        verify(curvePointRepository).save(argumentCaptor.capture());

        CurvePoint newCurvePoint = argumentCaptor.getValue();
        assertEquals(1,newCurvePoint.getCurveId());
        assertEquals(2.2,newCurvePoint.getTerm());
        assertEquals(3.5,newCurvePoint.getValue());
    }

    @Test
    void update_shouldUpdate_whenIdExists(){
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        curvePointService.update(curvePointUpdate, 1);

        verify(curvePointRepository).save(curvePoint);
        assertEquals(curvePointUpdate.getCurveId(),curvePoint.getCurveId());
        assertEquals(curvePointUpdate.getTerm(),curvePoint.getTerm());
        assertEquals(curvePointUpdate.getValue(),curvePoint.getValue());
    }

    @Test
    void update_shouldUpdate_whenIdNotExists(){
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        curvePointService.update(curvePointUpdate, 1);

        verify(curvePointRepository,never()).save(any());
    }

    @Test
    void delete_shouldCallRepository() {
        curvePointService.delete(1);

        verify(curvePointRepository).deleteById(1);
    }

    @Test
    void getAll_shouldReturnList() {
        when(curvePointRepository.findAll()).thenReturn(List.of(curvePoint));

        List<CurvePoint> result = curvePointService.getAll();

        assertEquals(1,result.size());
        verify(curvePointRepository).findAll();
    }

    @Test
    void getById_shouldReturnList_whenIdExists() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        CurvePoint result = curvePointService.getById(1);

        assertEquals(curvePoint, result);
    }

    @Test
    void getById_shouldReturnList_whenIdNotExists() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> curvePointService.getById(1));
    }
}
