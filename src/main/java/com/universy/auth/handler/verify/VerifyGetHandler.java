package com.universy.auth.handler.verify;

import com.universy.auth.function.verify.VerifyGetConsumer;
import com.universy.auth.model.ResendConfirmation;
import com.universy.lambda.api.handlers.handler.apigateway.consumer.ConsumerHandler;

public class VerifyGetHandler extends ConsumerHandler<ResendConfirmation, VerifyGetConsumer> {


    @Override
    protected VerifyGetConsumer getConsumer() {
        return new VerifyGetConsumer();
    }

    @Override
    protected Class<ResendConfirmation> getInputClass() {
        return ResendConfirmation.class;
    }

    @Override
    protected boolean inputFromBody() {
        return Boolean.FALSE;
    }
}
