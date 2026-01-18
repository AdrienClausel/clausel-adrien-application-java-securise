package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dtos.UserDto;
import com.nnk.springboot.mappers.UserMapper;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.user.IUserService;
import com.nnk.springboot.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user/list")
    public String home(Model model)
    {
        var userDto = UserMapper.toDtoList(userService.getAll());
        model.addAttribute("usersDto", userDto);
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(@ModelAttribute("") UserDto UsersDto) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result) {

        if (result.hasErrors()) {
            return "user/add";
        }

        userService.add(UserMapper.toEntity(userDto));

        return "redirect:/user/list";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        var user = userService.getById(id);
        model.addAttribute("userDto", new UserDto(id, user.getFullname(),user.getUsername(), "", user.getRole()));
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserDto userDto,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        var user = UserMapper.toEntity(userDto);
        userService.update(user,id);

        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.delete(id);

        return "redirect:/user/list";
    }
}
