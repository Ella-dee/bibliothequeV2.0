package com.mbooks.microservicebooks.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {
    /**
     * <p>Exception if a request is invalid</p>
     * @param s
     */
    public InvalidRequestException(String s) {
        super(s);
    }
}
