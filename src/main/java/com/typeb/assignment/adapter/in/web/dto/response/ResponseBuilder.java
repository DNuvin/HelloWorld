package com.typeb.assignment.adapter.in.web.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class ResponseBuilder {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        ResponseCode rc = ResponseCode.SUCCESS;

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        rc.getCode(),
                        rc.getDefaultMessage(),
                        data,
                        List.of()
                )
        );
    }

    public static ResponseEntity<ApiResponse<Object>> error(
            HttpStatus status,
            ResponseCode rc,
            List<FieldErrorDetail> errors) {

        return ResponseEntity.status(status)
                .body(new ApiResponse<>(
                        false,
                        rc.getCode(),
                        rc.getDefaultMessage(),
                        null,
                        errors
                ));
    }
}