package com.universy.account.function.login;

import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.universy.account.cognito.actions.CognitoAction;
import com.universy.account.cognito.actions.InitiateAuth;
import com.universy.account.cognito.client.CloudCognitoClientSupplier;
import com.universy.account.cognito.client.CognitoClientSupplier;
import com.universy.account.cognito.wrappers.InitiateAuthResultWrapper;
import com.universy.account.function.login.exceptions.UserNotAuthorizedException;
import com.universy.account.function.exceptions.UserNotFoundInPoolException;
import com.universy.account.model.Token;
import com.universy.account.model.User;

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
