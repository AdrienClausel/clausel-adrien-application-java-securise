package com.nnk.springboot.services.rating;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService implements IRatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public void add(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public void update(Rating ratingUpdate, Integer id) {
        var optionnalRating = ratingRepository.findById(id);
        if (optionnalRating.isPresent()){
            var rating = optionnalRating.get();
            rating.setMoodysRating(ratingUpdate.getMoodysRating());
            rating.setFitchRating(ratingUpdate.getFitchRating());
            rating.setSandPRating(ratingUpdate.getSandPRating());
            rating.setOrderNumber(ratingUpdate.getOrderNumber());

            ratingRepository.save(rating);
        }
    }

    @Override
    public void delete(Integer id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating getById(Integer id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("rating not found: " + id));
    }
}
