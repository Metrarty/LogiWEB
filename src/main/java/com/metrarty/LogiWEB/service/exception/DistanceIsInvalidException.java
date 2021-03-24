package com.metrarty.LogiWEB.service.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class DistanceIsInvalidException extends RuntimeException {
    public DistanceIsInvalidException(String message) {
        super(message);
    }
}
