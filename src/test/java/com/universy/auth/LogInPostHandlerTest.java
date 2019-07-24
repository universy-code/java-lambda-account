package com.universy.auth;

import com.universy.auth.function.login.LogInPostFunction;
import com.universy.auth.handler.login.LogInPostHandler;
import com.universy.auth.model.Person;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LogInPostHandlerTest {

    private LogInPostHandler handler;

    @Before
    public void setUp(){
        handler = new LogInPostHandler();
    }

    @Test
    public void testReturnedTypes(){
        assertEquals(Person.class, handler.getInputClass());
        assertEquals(LogInPostFunction.class, handler.getFunction().getClass());
    }
}
