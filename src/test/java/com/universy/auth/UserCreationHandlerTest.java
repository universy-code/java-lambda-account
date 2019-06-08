package com.universy.auth;

import com.universy.auth.function.UserCreationFunction;
import com.universy.auth.function.UserCreationFunctionTest;
import com.universy.auth.model.Token;
import com.universy.auth.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserCreationHandlerTest {

    private UserCreationHandler handler;

    @Before
    public void setUp(){
        handler = new UserCreationHandler();
    }

    @Test
    public void testReturnedTypes(){
        assertEquals(User.class, handler.getInputClass());
        assertEquals(Token.class, handler.getOutputClass());
        assertEquals(UserCreationFunction.class, handler.getFunctionClass());
    }
}
