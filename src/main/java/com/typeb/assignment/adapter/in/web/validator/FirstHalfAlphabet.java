package com.typeb.assignment.adapter.in.web.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FirstHalfAlphabetValidator.class)
@Documented
public @interface FirstHalfAlphabet {
    String message() default "Invalid Input";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}