package com.typeb.assignment.infrastructure.exception;

import com.typeb.assignment.adapter.in.web.dto.request.RequestEntityInterface;
import jakarta.validation.ConstraintViolation;
import java.util.*;

/**
 * Custom exception to wrap JSR-380 (Bean Validation) errors for request DTOs.
 * Stores errors in a map where the key is the field name and value is a list of error messages.
 */
public class ValidationException extends RuntimeException {

    private final transient Map<String, List<String>> errors;

    /**
     * Constructor used when validating a RequestEntityInterface DTO.
     *
     * @param violations Set of constraint violations
     */
    public ValidationException(Set<ConstraintViolation<RequestEntityInterface>> violations) {
        super("Validation failed");
        this.errors = formatValidationErrors(violations);
    }

    /**
     * Constructor for pre-built error map
     *
     * @param errors Map of field -> error messages
     */
    public ValidationException(Map<String, List<String>> errors) {
        super("Validation failed");
        this.errors = errors;
    }

    /**
     * Get errors map
     *
     * @return Map of field -> list of error messages
     */
    public Map<String, List<String>> getErrors() {
        return errors;
    }

    /**
     * Convert JSR-380 ConstraintViolations into a map suitable for JSON response
     *
     * @param violations Set of constraint violations
     * @return Map of field -> list of error messages
     */
    private Map<String, List<String>> formatValidationErrors(
            Set<ConstraintViolation<RequestEntityInterface>> violations) {

        Map<String, List<String>> errDetails = new LinkedHashMap<>();

        for (ConstraintViolation<RequestEntityInterface> violation : violations) {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();

            // Add message to existing list if key exists
            errDetails.computeIfAbsent(field, k -> new ArrayList<>()).add(message);
        }

        return errDetails;
    }
}