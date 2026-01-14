package com.nnk.springboot.services.rating;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface IRatingService {
    void add(Rating rating);
    void update(Rating rating, Integer id);
    void delete(Integer id);
    List<Rating> getAll();
    Rating getById(Integer id);
}
