package com.nnk.springboot.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception ex, Model model){

        model.addAttribute("errorMessage","An unexpected error occured. Please try again later.");
        return "error/error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleBadRequest(IllegalArgumentException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/error";
    }
}
