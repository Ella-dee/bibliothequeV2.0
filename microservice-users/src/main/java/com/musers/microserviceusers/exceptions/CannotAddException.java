package com.musers.microserviceusers.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CannotAdddException extends RuntimeException {
    /**
     * <p>Exception if a query returns "can't add new user"</p>
     * @param s
     */
    public CannotAdddException(String s) {
        super(s);
    }
}
