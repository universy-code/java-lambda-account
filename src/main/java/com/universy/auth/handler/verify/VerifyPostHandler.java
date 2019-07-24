package com.universy.auth.handler.verify;

import com.universy.auth.function.verify.VerifyPostFunction;
import com.universy.auth.model.SignUpConfirmation;
import com.universy.auth.model.Token;
import com.universy.lambda.api.handlers.handler.apigateway.function.FunctionHandler;

public class VerifyPostHandler extends FunctionHandler<SignUpConfirmation, Token, VerifyPostFunction> {

    @Override
    protected VerifyPostFunction getFunction() {
        return new VerifyPostFunction();
    }

    @Override
    protected Class<SignUpConfirmation> getInputClass() {
        return SignUpConfirmation.class;
    }
}
