package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.service.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * Rest exception handler.
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Handles {@link EntityNotFoundException}.
     * @param ex EntityNotFoundException
     * @return bad request response entity
     */
    @ExceptionHandler
    public ResponseEntity<String> handle(EntityNotFoundException ex) {
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


    /**
     * Handles (@link MethodArgumentNotValidException)
     * @param ex MethodArgumentNotValidException
     * @return list of bad request responses
     */
    @ExceptionHandler
    public ResponseEntity<List<String>> handle(final MethodArgumentNotValidException ex) {
        final List<String> result = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            result.add(error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(result);
    }

    /**
     * Handles (@link MethodArgumentNotValidException)
     * @param ex MethodArgumentNotValidException
     * @return bad request response entity
     */
    @ExceptionHandler
    public ResponseEntity<String> handle(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("HTTP body is not readable");
    }
}
