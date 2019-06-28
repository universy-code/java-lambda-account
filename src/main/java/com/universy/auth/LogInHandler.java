package com.universy.auth;

import com.universy.auth.function.LogInFunction;
import com.universy.auth.model.Token;
import com.universy.auth.model.User;
import com.universy.lambda.api.handlers.handler.apigateway.function.FunctionHandler;

public class LogInHandler extends FunctionHandler<User, Token, LogInFunction> {

    @Override
    protected LogInFunction getFunction() {
        return new LogInFunction();
    }

    @Override
    protected Class<User> getInputClass() {
        return User.class;
    }
}
