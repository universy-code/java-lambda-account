package com.universy.account.handler.verify;

import com.universy.account.function.verify.VerifyPostFunction;
import com.universy.account.model.SignUpConfirmation;
import com.universy.account.model.Token;
import com.universy.lambda.api.handlers.handler.apigateway.function.FunctionHandler;

import java.util.function.Function;

public class VerifyPostHandler extends FunctionHandler<SignUpConfirmation, Token> {

    @Override
    protected Function<SignUpConfirmation, Token> getFunction() {
        return new VerifyPostFunction();
    }

    @Override
    protected Class<SignUpConfirmation> getInputClass() {
        return SignUpConfirmation.class;
    }
}