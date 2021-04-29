package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

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
}

//todo: How to write test for RestExceptionHandler?

