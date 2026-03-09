package com.typeb.assignment.adapter.in.web.dto.request;

import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import com.typeb.assignment.infrastructure.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class RequestEntityValidator {

    private final Validator validator;

    public RequestEntityValidator(Validator validator) {
        this.validator = validator;
    }

    public void validate(RequestEntityInterface target) {
        Set<ConstraintViolation<RequestEntityInterface>> errors = validator.validate(target);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
