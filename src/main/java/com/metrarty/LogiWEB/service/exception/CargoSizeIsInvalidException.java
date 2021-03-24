package com.metrarty.LogiWEB.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CargoSizeIsInvalidException extends RuntimeException {
    public CargoSizeIsInvalidException(String message) {
        super(message);
    }
}