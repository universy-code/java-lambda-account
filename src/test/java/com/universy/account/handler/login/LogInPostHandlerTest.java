package com.universy.account.handler.login;

import com.universy.account.function.login.LogInPostFunction;
import com.universy.account.model.User;
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
