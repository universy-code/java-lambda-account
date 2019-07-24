package com.universy.auth.cognito.actions;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.universy.auth.cognito.client.CognitoClientSupplier;
import com.universy.auth.cognito.wrappers.InitiateAuthResultWrapper;
import com.universy.auth.environment.Environment;
import com.universy.auth.model.Person;
import com.universy.auth.model.User;

public class InitiateAuth implements CognitoAction<User, InitiateAuthResultWrapper> {

    private static final String USERNAME_PARAMETER_NAME = "USERNAME";
    private static final String PASSWORD_PARAMETER_NAME = "PASSWORD";


    private final AWSCognitoIdentityProvider identityProvider;

    public InitiateAuth(CognitoClientSupplier clientSupplier){
        identityProvider = clientSupplier.get();
    }

    @Override
    public InitiateAuthResultWrapper perform(User user) {
        InitiateAuthRequest initiateAuthRequest = createInitiateAuthRequestForUser(user);
        return new InitiateAuthResultWrapper(identityProvider.initiateAuth(initiateAuthRequest));
    }

    private InitiateAuthRequest createInitiateAuthRequestForUser(User user) {
        return new InitiateAuthRequest()
                .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .withClientId(Environment.getClientID())
                .addAuthParametersEntry(USERNAME_PARAMETER_NAME, user.getUsername())
                .addAuthParametersEntry(PASSWORD_PARAMETER_NAME, user.getPassword());
    }
}
