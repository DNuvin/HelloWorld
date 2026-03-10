package com.typeb.assignment.adapter.in.web.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class ApiResponse<T> {

    private LocalDateTime timestamp;
    private boolean success;
    private String code;
    private String message;
    private T data;
    private List<FieldErrorDetail> errors;

    public ApiResponse(boolean success, String code, String message, T data, List<FieldErrorDetail> errors) {
        this.timestamp = LocalDateTime.now();
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public boolean isSuccess() { return success; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public List<FieldErrorDetail> getErrors() { return errors; }
}
