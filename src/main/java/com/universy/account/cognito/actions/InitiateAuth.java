package com.universy.account.cognito.actions;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.universy.account.model.User;
import com.universy.cognito.CognitoIdentityProviderFactory;
import com.universy.cognito.actions.CognitoAction;
import com.universy.cognito.environment.CognitoEnvironment;

public class InitiateAuth implements CognitoAction<User, InitiateAuthResult> {

    private static final String USERNAME_PARAMETER_NAME = "USERNAME";
    private static final String PASSWORD_PARAMETER_NAME = "PASSWORD";


    private final AWSCognitoIdentityProvider identityProvider;

    public InitiateAuth() {
        this(CognitoIdentityProviderFactory.createIdentityProvider());
    }

    public InitiateAuth(AWSCognitoIdentityProvider identityProvider) {
        this.identityProvider = identityProvider;
    }

    @Override
    public InitiateAuthResult perform(User user) {
        InitiateAuthRequest initiateAuthRequest = createInitiateAuthRequestForUser(user);
        return identityProvider.initiateAuth(initiateAuthRequest);
    }

    private InitiateAuthRequest createInitiateAuthRequestForUser(User user) {
        return new InitiateAuthRequest()
                .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .withClientId(CognitoEnvironment.getClientID())
                .addAuthParametersEntry(USERNAME_PARAMETER_NAME, user.getUsername())
                .addAuthParametersEntry(PASSWORD_PARAMETER_NAME, user.getPassword());
    }
}
