package com.nnk.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserDto(
      Integer id,
      @NotBlank(message = "fullname is mandatory")
      String fullname,
      @NotBlank(message = "username is mandatory")
      String username,
      @NotBlank(message = "password is mandatory")
      @Pattern(
              regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
              message = "The password must contain at least 8 characters, one uppercase letter, one number, and one symbol"
      )
      String password,
      @NotBlank(message = "role is mandatory")
      String role
) {
}
