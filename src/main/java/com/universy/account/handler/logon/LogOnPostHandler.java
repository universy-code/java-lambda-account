package com.universy.account.handler.logon;

import com.universy.account.function.logon.LogOnPostConsumer;
import com.universy.account.model.Person;
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