package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dtos.RuleNameDto;
import com.nnk.springboot.mappers.RuleNameMapper;
import com.nnk.springboot.services.ruleName.IRuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class RuleNameController {

    @Autowired
    private IRuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        var ruleNamesDto = RuleNameMapper.toDtoList(ruleNameService.getAll());
        model.addAttribute("ruleNamesDto", ruleNamesDto);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(@ModelAttribute("ruleNameDto") RuleNameDto ruleNameDto) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid @ModelAttribute("ruleNameDto") RuleNameDto ruleNameDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "ruleName/add";
        }

        ruleNameService.add(RuleNameMapper.toEntity(ruleNameDto));

        return "redirect:/ruleName/list";

    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            var ruleName = ruleNameService.getById(id);
            model.addAttribute("ruleNameDto", new RuleNameDto(ruleName.getId(), ruleName.getName(),ruleName.getDescription(), ruleName.getJson(), ruleName.getTemplate(), ruleName.getSqlStr(), ruleName.getSqlPart()));
            return "ruleName/update";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMsg", ex.getMessage());
            return "redirect:403";
        }
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameDto ruleNameDto,
                             BindingResult result, Model model) {
        if (result.hasErrors()){
            return "ruleName/update";
        }

        var ruleName = RuleNameMapper.toEntity(ruleNameDto);

        ruleNameService.update(ruleName, id);

        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.delete(id);

        return "redirect:/ruleName/list";
    }
}
