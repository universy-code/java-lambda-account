package com.universy.auth.handler.verify;

import com.universy.auth.function.verify.VerifyGetConsumer;
import com.universy.auth.model.ResendConfirmation;
import com.universy.lambda.api.handlers.handler.apigateway.consumer.ConsumerHandler;
import com.universy.lambda.api.handlers.handler.input.InputType;

import java.util.function.Consumer;

public class VerifyGetHandler extends ConsumerHandler<ResendConfirmation> {

    @Override
    protected Consumer<ResendConfirmation> getConsumer() {
        return new VerifyGetConsumer();
    }

    @Override
    protected Class<ResendConfirmation> getInputClass() {
        return ResendConfirmation.class;
    }

    @Override
    protected InputType inputType() {
        return InputType.QUERY_PARAMS;
    }
}