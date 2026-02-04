package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.rating.IRatingService;
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

@WebMvcTest(RatingController.class)
@WithMockUser
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRatingService ratingService;

    @Test
    void home_shouldReturnRatingListView() throws Exception {
        when(ratingService.getAll()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratingsDto"));
    }

    @Test
    void addRatingForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    void validate_shouldRedirect_whenNoErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                        .with(csrf())
                        .param("moodysRating", "Aaa")
                        .param("sandPRating", "AAA")
                        .param("fitchRating", "AAA")
                        .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).add(any());
    }

    @Test
    void validate_shouldReturnAddView_whenErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                        .with(csrf())
                        .param("moodysRating", "")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .param("orderNumber", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));

        verify(ratingService, never()).add(any());
    }

    @Test
    void showUpdateForm_shouldReturnUpdateView() throws Exception {
        Rating rating = new Rating("Aaa", "AAA", "AAA", 1);
        when(ratingService.getById(1)).thenReturn(rating);

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("ratingDto"));
    }

    @Test
    void updateRating_shouldRedirect_whenNoErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/1")
                        .with(csrf())
                        .param("moodysRating", "Aaa")
                        .param("sandPRating", "AAA")
                        .param("fitchRating", "AAA")
                        .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).update(any(), eq(1));
    }

    @Test
    void deleteRating_shouldRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).delete(1);
    }
}
