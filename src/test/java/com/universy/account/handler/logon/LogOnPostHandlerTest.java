package com.universy.account.handler.logon;

import com.universy.account.function.logon.LogOnPostConsumer;
import com.universy.account.model.Person;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LogOnPostHandlerTest {

    private LogOnPostHandler handler;

    @Before
    public void setUp(){
        handler = new LogOnPostHandler();
    }

    @Test
    public void testReturnedTypes(){
        assertEquals(Person.class, handler.getInputClass());
        assertEquals(LogOnPostConsumer.class, handler.getConsumer().getClass());
    }
}
