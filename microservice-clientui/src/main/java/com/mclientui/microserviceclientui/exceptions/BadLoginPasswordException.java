package com.mclientui.microserviceclientui.exceptions;

public class BadLoginPasswordException extends RuntimeException {
    public BadLoginPasswordException(String message) {
        super(message);
    }
}
