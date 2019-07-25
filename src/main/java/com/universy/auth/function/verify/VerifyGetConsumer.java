package com.universy.auth.function.verify;

import com.amazonaws.services.cognitoidp.model.ResendConfirmationCodeResult;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.universy.auth.cognito.actions.CognitoAction;
import com.universy.auth.cognito.actions.ResendConfirmationCode;
import com.universy.auth.cognito.client.CloudCognitoClientSupplier;
import com.universy.auth.cognito.client.CognitoClientSupplier;
import com.universy.auth.cognito.wrappers.ResultWrapper;
import com.universy.auth.function.exceptions.UserNotFoundInPoolException;
import com.universy.auth.model.ResendConfirmation;

import java.util.function.Consumer;

public class VerifyGetConsumer implements Consumer<ResendConfirmation> {

    private final CognitoAction<ResendConfirmation, ResultWrapper<ResendConfirmationCodeResult>> resendCodeAction;

    public VerifyGetConsumer() {
        this(new CloudCognitoClientSupplier());
    }

    public VerifyGetConsumer(CognitoClientSupplier clientSupplier) {
        this(new ResendConfirmationCode(clientSupplier));
    }

    public VerifyGetConsumer(CognitoAction<ResendConfirmation, ResultWrapper<ResendConfirmationCodeResult>> resendCodeAction) {
        this.resendCodeAction = resendCodeAction;
    }

    @Override
    public void accept(ResendConfirmation resendConfirmation) {

        try {

            resendCodeAction.perform(resendConfirmation);

        } catch (UserNotFoundException e){
            throw new UserNotFoundInPoolException(resendConfirmation);
        }

    }
}