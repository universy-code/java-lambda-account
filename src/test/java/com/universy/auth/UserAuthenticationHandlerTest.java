package com.universy.auth;

import com.universy.auth.function.UserAuthenticationFunction;
import com.universy.auth.model.Token;
import com.universy.auth.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserAuthenticationHandlerTest {

    private UserAuthenticatorHandler handler;

    @Before
    public void setUp(){
        handler = new UserAuthenticatorHandler();
    }

    @Test
    public void testReturnedTypes(){
        assertEquals(User.class, handler.getInputClass());
        assertEquals(Token.class, handler.getOutputClass());
        assertEquals(UserAuthenticationFunction.class, handler.getFunctionClass());
    }
}
