package com.mbooks.microservicebooks.exceptions;

public class CannotAddException extends RuntimeException {
    public CannotAddException(String message) {
        super(message);
    }
}
