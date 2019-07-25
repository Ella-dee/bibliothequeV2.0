package com.mbooks.microservicebooks.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDateException extends RuntimeException {
    /**
     * <p>Exception if a date is invalid</p>
     * @param s
     */
    public InvalidDateException(String s) {
        super(s);
    }
}
