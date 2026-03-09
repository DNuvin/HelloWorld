package com.typeb.assignment.domain.exception;

public class InvalidNameException extends RuntimeException {
    public InvalidNameException() {
        super("Invalid Input");
    }
}
