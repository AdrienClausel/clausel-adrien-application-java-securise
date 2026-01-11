package com.nnk.springboot.controllers;

import com.nnk.springboot.dtos.CurvePointDto;
import com.nnk.springboot.mappers.CurvePointMapper;
import com.nnk.springboot.services.curvePoint.ICurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class CurveController {

    @Autowired
    private ICurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        var curvePointsDto = CurvePointMapper.toDoList(curvePointService.getAll());
        model.addAttribute("curvePointListDto", curvePointsDto);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(Model model) {
        model.addAttribute("curvePointDto", new CurvePointDto(0,null,0.0,0.0));
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid @ModelAttribute("curvePointDto") CurvePointDto curvePointDto, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "curvePoint/add";
        }

        curvePointService.add(CurvePointMapper.toEntity(curvePointDto));

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            var curvePoint = curvePointService.getById(id);
            model.addAttribute("curvePointDto", new CurvePointDto(id, curvePoint.getCurveId(), curvePoint.getTerm(), curvePoint.getValue()));
            return "curvePoint/update";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMsg", ex.getMessage());
            return "redirect:403";
        }
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePointDto curvePointDto,
                             BindingResult result, Model model) {
        if (result.hasErrors()){
            return "curvePoint/add";
        }

        var curvePoint = CurvePointMapper.toEntity(curvePointDto);

        curvePointService.update(curvePoint, id);

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        curvePointService.delete(id);

        return "redirect:/curvePoint/list";
    }
}
