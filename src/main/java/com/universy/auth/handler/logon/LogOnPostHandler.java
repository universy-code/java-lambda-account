package com.universy.auth.handler.logon;

import com.universy.auth.function.logon.LogOnPostConsumer;
import com.universy.auth.model.Person;
import com.universy.lambda.api.handlers.handler.apigateway.consumer.ConsumerHandler;

public class LogOnPostHandler extends ConsumerHandler<Person, LogOnPostConsumer> {

    @Override
    protected LogOnPostConsumer getConsumer() {
        return new LogOnPostConsumer();
    }

    @Override
    protected Class<Person> getInputClass() {
        return Person.class;
    }
}
