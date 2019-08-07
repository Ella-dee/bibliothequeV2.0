package com.mmailing.microservicemailing.exceptions;

public class CannotSendException extends RuntimeException {
    public CannotSendException(String message) {
        super(message);
    }
}
