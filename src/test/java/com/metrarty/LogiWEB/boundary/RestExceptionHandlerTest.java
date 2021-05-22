package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import com.metrarty.LogiWEB.service.exception.ValueIsInvalidException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.List;


public class RestExceptionHandlerTest {

    private RestExceptionHandler restExceptionHandler;

    @Before
    public void init() {
        restExceptionHandler = new RestExceptionHandler();
    }

    @Test
    public void handleEntityNotFoundException() {
        //prepare
        EntityNotFoundException ex = new EntityNotFoundException("Test message");
        ResponseEntity<String> expected = ResponseEntity.badRequest().body("Test message");
        // run
        ResponseEntity<String> actual = restExceptionHandler.handle(ex);
        //test
        Assert.assertEquals("Must be equal", expected, actual);
    }

    @Test
    public void handleValueIsInvalidException() {
        //prepare
        ValueIsInvalidException ex = new ValueIsInvalidException("Test message");
        ResponseEntity<String> expected = ResponseEntity.badRequest().body("Test message");
        //run
        ResponseEntity<String> actual = restExceptionHandler.handle(ex);
        //test
        Assert.assertEquals("Must be equal", expected, actual);
    }

    @Test
    public void handleMethodArgumentNotValidException() {
        //prepare;
        ObjectError error = new ObjectError("testObject", "Test message");
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(error, "testObject");
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        ex.addError(error);

        List<String> result = Collections.singletonList("Test message");
        ResponseEntity<List<String>> expected = ResponseEntity.badRequest().body(result);

        //run
        ResponseEntity<List<String>> actual = restExceptionHandler.handle(ex);
        //test
        Assert.assertEquals("Must be equal", expected, actual);
    }

    @Test
    public void handleHttpMessageNotReadableException() {
        //prepare
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("HTTP body is not readable");
        ResponseEntity<String> expected = ResponseEntity.badRequest().body("HTTP body is not readable");
        //run
        ResponseEntity<String> actual = restExceptionHandler.handle(ex);
        //test
        Assert.assertEquals("Must be equal", expected, actual);
    }
}

