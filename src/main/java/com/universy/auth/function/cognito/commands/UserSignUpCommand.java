package com.universy.auth.function.cognito.commands;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminConfirmSignUpRequest;
import com.amazonaws.services.cognitoidp.model.AdminConfirmSignUpResult;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.universy.auth.function.cognito.CognitoCommand;
import com.universy.auth.function.cognito.wrappers.AdminConfirmSignUpResultWrapper;
import com.universy.auth.model.User;
import com.universy.auth.util.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserSignUpCommand extends CognitoCommand<AdminConfirmSignUpResultWrapper> {

    private static Logger LOGGER = LogManager.getLogger(UserSignUpCommand.class);

    public UserSignUpCommand() {
    }

    public UserSignUpCommand(AWSCognitoIdentityProvider provider) {
        super(provider);
    }

    @Override
    protected AdminConfirmSignUpResultWrapper performAction(User user) {
        SignUpRequest signUpRequest = createSignUpRequest(user);
        getProvider().signUp(signUpRequest);

        AdminConfirmSignUpRequest confirmSignUpRequest = createConfirmSignUpRequest(user);
        AdminConfirmSignUpResult confirmSignUpResult = getProvider().adminConfirmSignUp(confirmSignUpRequest);

        return new AdminConfirmSignUpResultWrapper(confirmSignUpResult);
    }

    private AdminConfirmSignUpRequest createConfirmSignUpRequest(User user) {
        LOGGER.info("Creating confirm sign up request...");
        return new AdminConfirmSignUpRequest()
                .withUsername(user.getMail())
                .withUserPoolId(Environment.getUserPoolID());
    }

    private SignUpRequest createSignUpRequest(User user) {
        LOGGER.info("Creating sign up request...");
        return new SignUpRequest()
                .withClientId(Environment.getClientID())
                .withUsername(user.getMail())
                .withPassword(user.getPassword());
    }
}
