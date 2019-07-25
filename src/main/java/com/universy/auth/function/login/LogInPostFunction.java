package com.universy.auth.function.login;

import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.universy.auth.cognito.actions.CognitoAction;
import com.universy.auth.cognito.actions.InitiateAuth;
import com.universy.auth.cognito.client.CloudCognitoClientSupplier;
import com.universy.auth.cognito.client.CognitoClientSupplier;
import com.universy.auth.cognito.wrappers.InitiateAuthResultWrapper;
import com.universy.auth.function.login.exceptions.UserNotAuthorizedException;
import com.universy.auth.function.exceptions.UserNotFoundInPoolException;
import com.universy.auth.model.Token;
import com.universy.auth.model.User;

import java.util.function.Function;

public class LogInPostFunction implements Function<User, Token> {

    private final CognitoAction<User, InitiateAuthResultWrapper> initiateAuthAction;

    public LogInPostFunction() {
        this(new CloudCognitoClientSupplier());
    }

    public LogInPostFunction(CognitoClientSupplier clientSupplier) {
        this(new InitiateAuth(clientSupplier));
    }

    public LogInPostFunction(CognitoAction<User, InitiateAuthResultWrapper> initiateAuthAction) {
        this.initiateAuthAction = initiateAuthAction;
    }

    @Override
    public Token apply(User user) {
        InitiateAuthResultWrapper authResult;
        try {

            authResult = initiateAuthAction.perform(user);

        } catch (UserNotFoundException e) {
            throw new UserNotFoundInPoolException(user);
        } catch (NotAuthorizedException e) {
            throw new UserNotAuthorizedException(user);
        }

        return new Token(user.getUsername(), authResult.getIdToken());
    }

}
