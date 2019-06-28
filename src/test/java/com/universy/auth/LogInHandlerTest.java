package com.universy.auth;

import com.universy.auth.function.LogInFunction;
import com.universy.auth.model.Token;
import com.universy.auth.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LogInHandlerTest {

    private LogInHandler handler;

    @Before
    public void setUp(){
        handler = new LogInHandler();
    }

    @Test
    public void testReturnedTypes(){
        assertEquals(User.class, handler.getInputClass());
        assertEquals(LogInFunction.class, handler.getFunction().getClass());
    }
}
