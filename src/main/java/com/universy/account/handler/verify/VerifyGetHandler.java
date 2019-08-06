package com.universy.account.handler.verify;

import com.universy.account.function.verify.VerifyGetConsumer;
import com.universy.account.model.ResendConfirmation;
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