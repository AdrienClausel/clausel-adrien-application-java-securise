package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.CurvePointController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.curvePoint.ICurvePointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CurvePointController.class)
@WithMockUser
class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICurvePointService curvePointService;

    @Test
    void home_shouldReturnCurvePointListView() throws Exception {
        when(curvePointService.getAll()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePointsDto"));
    }

    @Test
    void addCurvePointForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    void validate_shouldRedirect_whenNoErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                        .with(csrf())
                        .param("curveId", "1")
                        .param("term", "10")
                        .param("value", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).add(any());
    }

    @Test
    void validate_shouldReturnAddView_whenErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                        .with(csrf())
                        .param("curveId", "") // invalide
                        .param("term", "")
                        .param("value", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));

        verify(curvePointService, never()).add(any());
    }

    @Test
    void showUpdateForm_shouldReturnUpdateView() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1, 10d, 20d);
        when(curvePointService.getById(1)).thenReturn(curvePoint);

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePointDto"));
    }

    @Test
    void updateCurvePoint_shouldRedirect_whenNoErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
                        .with(csrf())
                        .param("curveId", "1")
                        .param("term", "10")
                        .param("value", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).update(any(), eq(1));
    }

    @Test
    void deleteCurvePoint_shouldRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).delete(1);
    }
}
