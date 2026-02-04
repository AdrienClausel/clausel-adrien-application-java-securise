package com.nnk.springboot.annotations;

import com.nnk.springboot.validators.UniqueUsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Documented
public @interface UniqueUsernameAnnotation {

    String message() default "username already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
