package com.universy.auth.function.cognito.commands;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.universy.auth.function.cognito.CognitoCommand;
import com.universy.auth.function.cognito.wrappers.InitiateAuthResultWrapper;
import com.universy.auth.model.User;
import com.universy.auth.util.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserAuthenticationCommand extends CognitoCommand<InitiateAuthResultWrapper> {

    private static Logger LOGGER = LogManager.getLogger(UserAuthenticationCommand.class);
    private static final String USERNAME_PARAMETER_NAME = "USERNAME";
    private static final String PASSWORD_PARAMETER_NAME = "PASSWORD";


    public UserAuthenticationCommand() {
    }

    public UserAuthenticationCommand(AWSCognitoIdentityProvider provider) {
        super(provider);
    }

    @Override
    protected InitiateAuthResultWrapper performAction(User user) {
        InitiateAuthRequest initiateAuthRequest = createInitiateAuthRequestForUser(user);
        InitiateAuthResult initiateAuthResult = getProvider().initiateAuth(initiateAuthRequest);
        return new InitiateAuthResultWrapper(initiateAuthResult);
    }

    private InitiateAuthRequest createInitiateAuthRequestForUser(User user) {
        LOGGER.info("Creating auth request...");
        return new InitiateAuthRequest()
                .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .withClientId(Environment.getClientID())
                .addAuthParametersEntry(USERNAME_PARAMETER_NAME, user.getMail())
                .addAuthParametersEntry(PASSWORD_PARAMETER_NAME, user.getPassword());
    }
}
