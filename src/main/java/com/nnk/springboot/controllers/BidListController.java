package com.nnk.springboot.controllers;

import com.nnk.springboot.dtos.BidListDto;
import com.nnk.springboot.mappers.BidListMapper;
import com.nnk.springboot.services.IBidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;



@Controller
public class BidListController {

    @Autowired
    private IBidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        var bidListsDto = BidListMapper.toDtoList(bidListService.getAll());
        model.addAttribute("bidListsDto",bidListsDto);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("bidListDto",new BidListDto(0,"","",0));
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid @ModelAttribute("bidListDto") BidListDto bidListDto, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list

        if (result.hasErrors()){
            return "bidList/add";
        }

        bidListService.add(BidListMapper.toEntity(bidListDto));

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            var bidList = bidListService.getById(id);
            model.addAttribute("bidListDto", new BidListDto(id, bidList.getAccount(), bidList.getType(), bidList.getBidQuantity()));
            return "bidList/update";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMsg", ex.getMessage());
            return "redirect:403";
        }
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListDto bidListDto,
                             BindingResult result, Model model) {
        if (result.hasErrors()){
            return "bidList/add";
        }

        var bidList = BidListMapper.toEntity(bidListDto);

        bidListService.update(bidList,id);

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.delete(id);

        return "redirect:/bidList/list";
    }
}
