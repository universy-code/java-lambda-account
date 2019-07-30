package com.universy.auth.cognito.actions;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;
import com.universy.auth.cognito.client.CognitoClientSupplier;
import com.universy.auth.cognito.wrappers.ResultWrapper;
import com.universy.auth.environment.Environment;
import com.universy.auth.model.SignUpConfirmation;

public class ConfirmSignUp implements CognitoAction<SignUpConfirmation, ResultWrapper<ConfirmSignUpResult>> {

    private final AWSCognitoIdentityProvider identityProvider;

    public ConfirmSignUp(CognitoClientSupplier clientSupplier) {
        identityProvider = clientSupplier.get();
    }


    @Override
    public ResultWrapper<ConfirmSignUpResult> perform(SignUpConfirmation signUpConfirmation) {
        ConfirmSignUpRequest confirmSignUpRequest = createConfirmSignUpRequest(signUpConfirmation);
        return ResultWrapper.wrap(identityProvider.confirmSignUp(confirmSignUpRequest));
    }

    private ConfirmSignUpRequest createConfirmSignUpRequest(SignUpConfirmation signUpConfirmation) {
        return new ConfirmSignUpRequest()
                .withClientId(Environment.getClientID())
                .withConfirmationCode(signUpConfirmation.getCode())
                .withUsername(signUpConfirmation.getUsername());
    }
}