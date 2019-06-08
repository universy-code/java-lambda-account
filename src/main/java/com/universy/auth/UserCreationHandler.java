package com.universy.auth;

import com.universy.auth.function.UserCreationFunction;
import com.universy.auth.model.Token;
import com.universy.auth.model.User;
import com.universy.common.lambda.api.handler.BaseAPIGatewayBodyHandler;

public class UserCreationHandler extends BaseAPIGatewayBodyHandler<User, Token, UserCreationFunction> {

    @Override
    protected Class<UserCreationFunction> getFunctionClass() {
        return UserCreationFunction.class;
    }

    @Override
    protected Class<User> getInputClass() {
        return User.class;
    }

    @Override
    protected Class<Token> getOutputClass() {
        return Token.class;
    }
}
