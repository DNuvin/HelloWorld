package com.typeb.assignment.adapter.in.web.dto.response;


public enum ResponseCode {

    SUCCESS("GEN-SUC-200", "Success"),

    VALIDATION_ERROR("GEN-VAL-400", "Validation failed"),

    INVALID_REQUEST("GEN-REQ-400", "Invalid request"),

    INTERNAL_ERROR("GEN-SYS-500", "Something went wrong");

    private final String code;
    private final String defaultMessage;

    ResponseCode(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public String getCode() { return code; }
    public String getDefaultMessage() { return defaultMessage; }
}
