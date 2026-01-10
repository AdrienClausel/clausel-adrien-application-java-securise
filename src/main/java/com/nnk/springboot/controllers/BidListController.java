package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
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
        model.addAttribute("bidLists",bidListService.getAll());
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

        bidListService.add(bidListDto);

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
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
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()){
            return "bidList/add";
        }

        bidListService.update(bidListDto,id);

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        bidListService.delete(id);

        return "redirect:/bidList/list";
    }
}
