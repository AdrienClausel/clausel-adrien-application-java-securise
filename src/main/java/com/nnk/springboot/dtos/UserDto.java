package com.nnk.springboot.dtos;

public record UserDto(
      Integer id,
      String fullName,
      String username,
      String password,
      String role
) {
}
