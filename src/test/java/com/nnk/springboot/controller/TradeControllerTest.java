package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.trade.TradeService;
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

@WebMvcTest(TradeController.class)
@WithMockUser
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @Test
    void home_shouldReturnTradeListView() throws Exception {
        when(tradeService.getAll()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("tradesDto"));

        verify(tradeService, times(1)).getAll();
    }

    @Test
    void addTrade_shouldReturnAddView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    void validate_shouldRedirect_whenNoErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                        .with(csrf())
                        .param("account", "AccountTest")
                        .param("type", "TypeTest")
                        .param("buyQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).add(any());
    }

    @Test
    void validate_shouldReturnAddView_whenErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                        .with(csrf())
                        .param("account", "")
                        .param("type", "")
                        .param("buyQuantity", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));

        verify(tradeService, never()).add(any());
    }

    @Test
    void showUpdateForm_shouldReturnUpdateView() throws Exception {
        Trade trade = new Trade("Account1", "Type1");
        when(tradeService.getById(1)).thenReturn(trade);

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("tradeDto"));

        verify(tradeService, times(1)).getById(1);
    }

    @Test
    void updateTrade_shouldRedirect_whenNoErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/1")
                        .with(csrf())
                        .param("account", "AccountUpdated")
                        .param("type", "TypeUpdated")
                        .param("buyQuantity", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).update(any(), eq(1));
    }

    @Test
    void deleteTrade_shouldRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).delete(1);
    }
}
