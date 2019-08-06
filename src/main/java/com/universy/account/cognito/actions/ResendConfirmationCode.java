package com.universy.account.cognito.actions;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.ResendConfirmationCodeRequest;
import com.amazonaws.services.cognitoidp.model.ResendConfirmationCodeResult;
import com.universy.account.cognito.client.CognitoClientSupplier;
import com.universy.account.cognito.wrappers.ResultWrapper;
import com.universy.account.environment.Environment;
import com.universy.account.model.ResendConfirmation;

public class ResendConfirmationCode implements CognitoAction<ResendConfirmation, ResultWrapper<ResendConfirmationCodeResult>> {

    private final AWSCognitoIdentityProvider identityProvider;

    public ResendConfirmationCode(CognitoClientSupplier clientSupplier) {
        identityProvider = clientSupplier.get();
    }


    @Override
    public ResultWrapper<ResendConfirmationCodeResult> perform(ResendConfirmation resendConfirmation) {
        ResendConfirmationCodeRequest codeRequest = createResendConfirmationCode(resendConfirmation);
        return ResultWrapper.wrap(identityProvider.resendConfirmationCode(codeRequest));
    }

    private ResendConfirmationCodeRequest createResendConfirmationCode(ResendConfirmation resendConfirmation) {
        return new ResendConfirmationCodeRequest()
                .withClientId(Environment.getClientID())
                .withUsername(resendConfirmation.getUsername());
    }
}
