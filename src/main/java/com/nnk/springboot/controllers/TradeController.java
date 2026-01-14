package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dtos.TradeDto;
import com.nnk.springboot.mappers.TradeMapper;
import com.nnk.springboot.services.trade.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        var tradesDto = TradeMapper.toDtoList(tradeService.getAll());
        model.addAttribute("tradesDto", tradesDto);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTrade(@ModelAttribute("tradeDto") TradeDto tradeDto) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid @ModelAttribute TradeDto tradeDto, BindingResult result, Model model) {

        if (result.hasErrors()){
            return "trade/add";
        }

        tradeService.add(TradeMapper.toEntity(tradeDto));

        return "redirect:/trade/list";

    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            var trade = tradeService.getById(id);
            model.addAttribute("tradeDto", new TradeDto(trade.getAccount(),trade.getType(),trade.getBuyQuantity()));
            return "trade/update";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMsg", ex.getMessage());
            return "redirect:403";
        }
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeDto tradeDto,
                             BindingResult result, Model model) {
        if (result.hasErrors()){
            return "trade/update";
        }

        var trade = TradeMapper.toEntity(tradeDto);

        tradeService.update(trade, id);

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.delete(id);

        return "redirect:/trade/list";
    }
}
