package com.universy.auth.handler.login;

import com.universy.auth.function.login.LogInPostFunction;
import com.universy.auth.model.Token;
import com.universy.auth.model.User;
import com.universy.lambda.api.handlers.handler.apigateway.function.FunctionHandler;

public class LogInPostHandler extends FunctionHandler<User, Token, LogInPostFunction> {

    @Override
    protected LogInPostFunction getFunction() {
        return new LogInPostFunction();
    }

    @Override
    protected Class<User> getInputClass() {
        return User.class;
    }
}
