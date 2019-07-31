package com.universy.auth.handler.logon;

import com.universy.auth.function.logon.LogOnPostConsumer;
import com.universy.auth.model.Person;
import com.universy.lambda.api.handlers.handler.apigateway.consumer.ConsumerHandler;

import java.util.function.Consumer;

public class LogOnPostHandler extends ConsumerHandler<Person> {

    @Override
    protected Consumer<Person> getConsumer() {
        return new LogOnPostConsumer();
    }

    @Override
    protected Class<Person> getInputClass() {
        return Person.class;
    }
}