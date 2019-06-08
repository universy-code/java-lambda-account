package com.universy.auth;

import com.universy.auth.function.UserAuthenticationFunction;
import com.universy.auth.model.Token;
import com.universy.auth.model.User;
import com.universy.common.lambda.api.handler.BaseAPIGatewayBodyHandler;

public class UserAuthenticatorHandler extends BaseAPIGatewayBodyHandler<User, Token, UserAuthenticationFunction> {

    @Override
    protected Class<UserAuthenticationFunction> getFunctionClass() {
        return UserAuthenticationFunction.class;
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
