package com.nnk.springboot.controllers;

import com.nnk.springboot.dtos.RatingDto;
import com.nnk.springboot.mappers.RatingMapper;
import com.nnk.springboot.services.rating.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class RatingController {

    @Autowired
    private IRatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        var ratingsDto = RatingMapper.toDtoList(ratingService.getAll());
        model.addAttribute("ratingsDto", ratingsDto);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(@ModelAttribute("ratingDto") RatingDto ratingDto) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid @ModelAttribute("ratingDto") RatingDto ratingDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "rating/add";
        }

        ratingService.add(RatingMapper.toEntity(ratingDto));

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        var rating = ratingService.getById(id);
        model.addAttribute("ratingDto", new RatingDto(id,rating.getMoodysRating(),rating.getSandPRating(),rating.getFitchRating(),rating.getOrderNumber()));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingDto ratingDto,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }

        var rating = RatingMapper.toEntity(ratingDto);

        ratingService.update(rating,id);

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.delete(id);
        return "redirect:/rating/list";
    }
}
