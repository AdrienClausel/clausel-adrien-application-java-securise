package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.bidList.IBidListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BidListController.class)
@WithMockUser
class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBidListService bidListService;

    @Test
    void home_shouldReturnBidListView() throws Exception {
        List<BidList> bidLists = List.of(
                new BidList("Account1", "Type1", 10d)
        );

        when(bidListService.getAll()).thenReturn(bidLists);

        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidListsDto"));
    }

    @Test
    void addBidForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    void validate_shouldRedirect_whenNoErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
                        .with(csrf())
                        .param("account", "AccountTest")
                        .param("type", "TypeTest")
                        .param("bidQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).add(any());
    }

    @Test
    void validate_shouldReturnAddView_whenErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
                        .with(csrf())
                        .param("account", "") // invalide
                        .param("type", "")
                        .param("bidQuantity", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));

        verify(bidListService, never()).add(any());
    }

    @Test
    void showUpdateForm_shouldReturnUpdateView() throws Exception {
        BidList bidList = new BidList("Account1", "Type1", 10d);
        when(bidListService.getById(1)).thenReturn(bidList);

        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidListDto"));
    }

    @Test
    void deleteBid_shouldRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).delete(1);
    }
}
