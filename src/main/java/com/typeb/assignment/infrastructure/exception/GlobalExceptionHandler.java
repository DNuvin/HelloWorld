package com.typeb.assignment.infrastructure.exception;

import com.typeb.assignment.adapter.in.web.dto.response.ApiResponse;
import com.typeb.assignment.adapter.in.web.dto.response.FieldErrorDetail;
import com.typeb.assignment.adapter.in.web.dto.response.ResponseBuilder;
import com.typeb.assignment.adapter.in.web.dto.response.ResponseCode;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    public static final String TRACE_ID = "traceId";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String traceId = MDC.get(TRACE_ID);
        log.error("Validation failed: MethodArgumentNotValidException [traceId={}]", traceId, ex);

        List<FieldErrorDetail> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new FieldErrorDetail(err.getField(), err.getDefaultMessage()))
                .toList();

        return ResponseBuilder.error(HttpStatus.BAD_REQUEST, ResponseCode.VALIDATION_ERROR, errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        String traceId = MDC.get(TRACE_ID);
        log.error("Validation failed: ConstraintViolationException [traceId={}]", traceId, ex);

        List<FieldErrorDetail> errors = ex.getConstraintViolations()
                .stream()
                .map(err -> new FieldErrorDetail(err.getPropertyPath().toString(), err.getMessage()))
                .toList();

        return ResponseBuilder.error(HttpStatus.BAD_REQUEST, ResponseCode.VALIDATION_ERROR, errors);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomValidation(ValidationException ex) {
        String traceId = MDC.get(TRACE_ID);
        log.error("Validation failed: Custom ValidationException [traceId={}]", traceId, ex);

        List<FieldErrorDetail> errors = ex.getErrors()
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue()
                        .stream()
                        .map(msg -> new FieldErrorDetail(entry.getKey(), msg)))
                .toList();

        return ResponseBuilder.error(HttpStatus.BAD_REQUEST, ResponseCode.VALIDATION_ERROR, errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAll(Exception ex) {
        String traceId = MDC.get(TRACE_ID);
        log.error("Unexpected error [traceId={}]", traceId, ex);

        List<FieldErrorDetail> errors = List.of(new FieldErrorDetail("server", ex.getMessage()));
        return ResponseBuilder.error(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.INTERNAL_ERROR, errors);
    }
}