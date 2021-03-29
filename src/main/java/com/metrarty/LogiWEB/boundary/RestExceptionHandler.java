package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.service.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Rest exception handler.
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Handles {@link ItemNotFoundException}.
     * @param ex ItemNotFoundException
     * @return bad request response entity
     */
    @ExceptionHandler
    public ResponseEntity<String> handle(ItemNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Handles (@link ValueIsInvalidException)
     * @param ex ValueIsInvalidException
     * @return bad request response entity
     */
    @ExceptionHandler
    public ResponseEntity<String> handle(ValueIsInvalidException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
