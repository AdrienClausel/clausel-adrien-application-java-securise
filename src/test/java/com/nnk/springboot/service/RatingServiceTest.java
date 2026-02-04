package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.rating.RatingService;
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
public class RatingServiceTest {

    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

    private Rating rating;
    private Rating ratingUpdate;

    @BeforeEach
    void setUp(){
        rating = new Rating("A","A","A",1);
        rating.setId(1);

        ratingUpdate = new Rating("AA","AA","AA",2);
    }

    @Test
    void add_shouldSave(){
        ratingService.add(rating);

        ArgumentCaptor<Rating> argumentCaptor = ArgumentCaptor.forClass(Rating.class);
        verify(ratingRepository).save(argumentCaptor.capture());

        Rating newRating = argumentCaptor.getValue();
        assertEquals("A",newRating.getMoodysRating());
        assertEquals("A",newRating.getSandPRating());
        assertEquals("A",newRating.getFitchRating());
        assertEquals(1,newRating.getOrderNumber());
    }

    @Test
    void update_shouldUpdate_whenIdExists(){
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        ratingService.update(ratingUpdate, 1);

        verify(ratingRepository).save(rating);
        assertEquals(ratingUpdate.getMoodysRating(), rating.getMoodysRating());
        assertEquals(ratingUpdate.getSandPRating(), rating.getSandPRating());
        assertEquals(ratingUpdate.getFitchRating(), rating.getFitchRating());
        assertEquals(ratingUpdate.getOrderNumber(), rating.getOrderNumber());
    }

    @Test
    void update_shouldUpdate_whenIdNotExists(){
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        ratingService.update(ratingUpdate, 1);

        verify(ratingRepository,never()).save(any());
    }

    @Test
    void delete_shouldCallRepository() {
        ratingService.delete(1);

        verify(ratingRepository).deleteById(1);
    }

    @Test
    void getAll_shouldReturnList() {
        when(ratingRepository.findAll()).thenReturn(List.of(rating));

        List<Rating> result = ratingService.getAll();

        assertEquals(1,result.size());
        verify(ratingRepository).findAll();
    }

    @Test
    void getById_shouldReturnList_whenIdExists() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Rating result = ratingService.getById(1);

        assertEquals(rating, result);
    }

    @Test
    void getById_shouldReturnList_whenIdNotExists() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> ratingService.getById(1));
    }
}
