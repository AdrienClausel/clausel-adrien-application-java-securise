package com.nnk.springboot.dtos;

public record UserDto(
      String fullName,
      String username,
      String password
) {
}
