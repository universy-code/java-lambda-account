package com.universy.account.function.verify;

import com.amazonaws.services.cognitoidp.model.ResendConfirmationCodeResult;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.universy.account.cognito.actions.ResendConfirmationCode;
import com.universy.account.function.exceptions.UserNotFoundInPoolException;
import com.universy.account.model.ResendConfirmation;
import com.universy.cognito.actions.CognitoAction;

import java.util.function.Consumer;

public class VerifyGetConsumer implements Consumer<ResendConfirmation> {

    private final CognitoAction<ResendConfirmation, ResendConfirmationCodeResult> resendCodeAction;

    public VerifyGetConsumer() {
        this(new ResendConfirmationCode());
    }

    public VerifyGetConsumer(CognitoAction<ResendConfirmation, ResendConfirmationCodeResult> resendCodeAction) {
        this.resendCodeAction = resendCodeAction;
    }

    @Override
    public void accept(ResendConfirmation resendConfirmation) {
        try {
            resendCodeAction.perform(resendConfirmation);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundInPoolException(resendConfirmation);
        }
    }
}