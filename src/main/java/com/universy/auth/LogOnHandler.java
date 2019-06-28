package com.universy.auth;

import com.universy.auth.function.LogOnFunction;
import com.universy.auth.model.Token;
import com.universy.auth.model.User;
import com.universy.lambda.api.handlers.handler.apigateway.function.FunctionHandler;

public class LogOnHandler extends FunctionHandler<User, Token, LogOnFunction> {

    @Override
    protected LogOnFunction getFunction() {
        return new LogOnFunction();
    }

    @Override
    protected Class<User> getInputClass() {
        return User.class;
    }
}
