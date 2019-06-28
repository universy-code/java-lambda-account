package com.universy.auth;

import com.universy.auth.function.LogOnFunction;
import com.universy.auth.model.Token;
import com.universy.auth.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LogOnHandlerTest {

    private LogOnHandler handler;

    @Before
    public void setUp(){
        handler = new LogOnHandler();
    }

    @Test
    public void testReturnedTypes(){
        assertEquals(User.class, handler.getInputClass());
        assertEquals(LogOnFunction.class, handler.getFunction().getClass());
    }
}
