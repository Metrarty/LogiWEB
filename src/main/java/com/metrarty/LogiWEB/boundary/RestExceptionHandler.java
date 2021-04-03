package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.service.exception.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
}
