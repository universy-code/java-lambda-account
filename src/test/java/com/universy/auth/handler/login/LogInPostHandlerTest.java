package com.universy.auth.handler.login;

import com.universy.auth.function.login.LogInPostFunction;
import com.universy.auth.model.Person;
import com.universy.auth.model.User;
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
        assertEquals(User.class, handler.getInputClass());
        assertEquals(LogInPostFunction.class, handler.getFunction().getClass());
    }
}
