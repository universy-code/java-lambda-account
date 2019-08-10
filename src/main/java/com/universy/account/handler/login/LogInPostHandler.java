package com.universy.account.handler.login;

import com.universy.account.function.login.LogInPostFunction;
import com.universy.account.model.Token;
import com.universy.account.model.User;
import com.universy.lambda.api.handlers.handler.apigateway.function.FunctionHandler;

import java.util.function.Function;

public class LogInPostHandler extends FunctionHandler<User, Token> {

    @Override
    protected Function<User, Token> getFunction() {
        return new LogInPostFunction();
    }

    @Override
    protected Class<User> getInputClass() {
        return User.class;
    }
}