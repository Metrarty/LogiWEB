package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.service.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Rest exception handler.
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Handles {@link CityNotFoundException}.
     * @param ex CityNotFoundException
     * @return bad request response entity
     */
    @ExceptionHandler
    public ResponseEntity<String> handle(CityNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Handles {@link DistanceNotFoundException}.
     * @param ex DistanceNotFoundException
     * @return bad request response entity
     */
    @ExceptionHandler
    public ResponseEntity<String> handle(DistanceNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Handles {@link TruckNotFoundException}.
     * @param ex TruckNotFoundException
     * @return bad request response entity
     */
    @ExceptionHandler
    public ResponseEntity<String> handle(TruckNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Handles {@link CargoNotFoundException}.
     * @param ex CargoNotFoundException
     * @return bad request response entity
     */
    @ExceptionHandler
    public ResponseEntity<String> handle(CargoNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Handles {@link OrderNotFoundException}
     * @param ex OrderNotFoundException
     * @return bad request response entity
     */
    @ExceptionHandler
    public ResponseEntity<String> handle(OrderNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(CargoSizeIsInvalidException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(DistanceIsInvalidException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
