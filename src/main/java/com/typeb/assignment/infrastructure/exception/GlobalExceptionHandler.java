package com.typeb.assignment.infrastructure.exception;


import com.typeb.assignment.adapter.in.web.dto.response.FieldErrorDetail;
import com.typeb.assignment.adapter.in.web.dto.response.ResponseBuilder;
import com.typeb.assignment.adapter.in.web.dto.response.ResponseCode;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    public GlobalExceptionHandler() {
        System.out.println("GlobalExceptionHandler instantiated");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        List<FieldErrorDetail> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new FieldErrorDetail(
                        err.getField(),
                        err.getDefaultMessage()))
                .toList();

        return ResponseBuilder.error(
                HttpStatus.BAD_REQUEST,
                ResponseCode.VALIDATION_ERROR,
                errors
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {

        List<FieldErrorDetail> errors = ex.getConstraintViolations()
                .stream()
                .map(err -> new FieldErrorDetail(
                        err.getPropertyPath().toString(),
                        err.getMessage()))
                .toList();

        return ResponseBuilder.error(
                HttpStatus.BAD_REQUEST,
                ResponseCode.VALIDATION_ERROR,
                errors
        );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleCustomValidation(ValidationException ex) {

        List<FieldErrorDetail> errors = ex.getErrors()
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue()
                        .stream()
                        .map(msg -> new FieldErrorDetail(entry.getKey(), msg)))
                .toList();

        return ResponseBuilder.error(
                HttpStatus.BAD_REQUEST,
                ResponseCode.VALIDATION_ERROR,
                errors
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception ex) {

        List<FieldErrorDetail> errors = List.of(
                new FieldErrorDetail("server", ex.getMessage())
        );

        return ResponseBuilder.error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ResponseCode.INTERNAL_ERROR,
                errors
        );
    }
}