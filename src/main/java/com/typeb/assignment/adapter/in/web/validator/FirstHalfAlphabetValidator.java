package com.typeb.assignment.adapter.in.web.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FirstHalfAlphabetValidator implements ConstraintValidator<FirstHalfAlphabet, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) return false;
        char first = Character.toLowerCase(value.charAt(0));
        return first >= 'a' && first <= 'm';
    }
}