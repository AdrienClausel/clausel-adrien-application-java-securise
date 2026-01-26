package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dtos.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public final class UserMapper {

    private UserMapper(){}

    public static User toEntity(UserDto userDto){
        User user = new User(userDto.username(), userDto.password(), userDto.fullname(), userDto.role());
        user.setId(user.getId());
        return user;
    }

    public static UserDto toDto(User user){
        return new UserDto(
                user.getId(),
                user.getFullname(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }

    public static List<UserDto> toDtoList(List<User> users){
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
}
