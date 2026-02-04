package com.nnk.springboot.validators;

import com.nnk.springboot.annotations.UniqueUsernameAnnotation;
import com.nnk.springboot.dtos.UserDto;
import com.nnk.springboot.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueUsernameValidator
        implements ConstraintValidator<UniqueUsernameAnnotation, UserDto> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(UserDto dto,
                           ConstraintValidatorContext context) {

        if (dto == null || dto.username() == null || dto.username().isBlank()) {
            return true;
        }

        boolean exists;

        if (dto.id() == null) {
            exists = userRepository.existsByUsername(dto.username());
        }
        else {
            exists = userRepository
                    .existsByUsernameAndIdNot(dto.username(), dto.id());
        }

        if (exists) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("username already exists")
                    .addPropertyNode("username")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}

