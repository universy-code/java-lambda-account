package com.universy.auth.function.verify;

import com.amazonaws.services.cognitoidp.model.CodeMismatchException;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.universy.auth.cognito.actions.CognitoAction;
import com.universy.auth.cognito.actions.ConfirmSignUp;
import com.universy.auth.cognito.actions.SignUp;
import com.universy.auth.cognito.client.CloudCognitoClientSupplier;
import com.universy.auth.cognito.client.CognitoClientSupplier;
import com.universy.auth.cognito.wrappers.ResultWrapper;
import com.universy.auth.function.exceptions.login.UserNotFoundInPoolException;
import com.universy.auth.model.Person;
import com.universy.auth.model.SignUpConfirmation;
import com.universy.auth.model.Token;

import java.util.function.Function;

public class VerifyPostFunction implements Function<SignUpConfirmation, Token> {

    private final CognitoAction<SignUpConfirmation, ResultWrapper<ConfirmSignUpResult>> signUpAction;

    public VerifyPostFunction() {
        this(new CloudCognitoClientSupplier());
    }

    public VerifyPostFunction(CognitoClientSupplier clientSupplier) {
        this(new ConfirmSignUp(clientSupplier));
    }

    public VerifyPostFunction(CognitoAction<SignUpConfirmation, ResultWrapper<ConfirmSignUpResult>> signUpAction) {
        this.signUpAction = signUpAction;
    }

    @Override
    public Token apply(SignUpConfirmation signUpConfirmation) {
        try {
            signUpAction.perform(signUpConfirmation);
        } catch (CodeMismatchException e){
            // TODO: Create the exception for the code not correct
        } catch (UserNotFoundException e){
            throw new UserNotFoundInPoolException(signUpConfirmation);
        }

        return null;
    }
}
