package com.universy.account.function.login;

import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.universy.account.cognito.actions.InitiateAuth;
import com.universy.account.cognito.transformers.CognitoTokenTransformer;
import com.universy.account.function.exceptions.UserNotFoundInPoolException;
import com.universy.account.function.login.exceptions.UserNotAuthorizedException;
import com.universy.account.model.Token;
import com.universy.account.model.User;
import com.universy.cognito.actions.CognitoAction;

import java.util.function.Function;

public class LogInPostFunction implements Function<User, Token> {

    private final CognitoAction<User, InitiateAuthResult> initiateAuthAction;

    public LogInPostFunction() {
        this(new InitiateAuth());
    }

    public LogInPostFunction(CognitoAction<User, InitiateAuthResult> initiateAuthAction) {
        this.initiateAuthAction = initiateAuthAction;
    }

    @Override
    public Token apply(User user) {
        try {
            InitiateAuthResult authResult = initiateAuthAction.perform(user);
            CognitoTokenTransformer cognitoTokenTransformer = new CognitoTokenTransformer(user, authResult);
            return cognitoTokenTransformer.transform();
        } catch (UserNotFoundException e) {
            throw new UserNotFoundInPoolException(user);
        } catch (NotAuthorizedException e) {
            throw new UserNotAuthorizedException(user);
        }
    }
}
