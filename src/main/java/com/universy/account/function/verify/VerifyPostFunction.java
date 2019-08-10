package com.universy.account.function.verify;

import com.amazonaws.services.cognitoidp.model.CodeMismatchException;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.universy.account.cognito.actions.ConfirmSignUp;
import com.universy.account.function.exceptions.UserNotFoundInPoolException;
import com.universy.account.function.login.LogInPostFunction;
import com.universy.account.function.verify.exceptions.IncorrectCodeException;
import com.universy.account.model.SignUpConfirmation;
import com.universy.account.model.Token;
import com.universy.account.model.User;
import com.universy.cognito.actions.CognitoAction;

import java.util.function.Function;

public class VerifyPostFunction implements Function<SignUpConfirmation, Token> {

    private final CognitoAction<SignUpConfirmation, ConfirmSignUpResult> signUpAction;

    public VerifyPostFunction() {
        this(new ConfirmSignUp());
    }

    public VerifyPostFunction(CognitoAction<SignUpConfirmation, ConfirmSignUpResult> signUpAction) {
        this.signUpAction = signUpAction;
    }

    @Override
    public Token apply(SignUpConfirmation signUpConfirmation) {
        try {
            signUpAction.perform(signUpConfirmation);
        } catch (CodeMismatchException e) {
            throw new IncorrectCodeException();
        } catch (UserNotFoundException e) {
            throw new UserNotFoundInPoolException(signUpConfirmation);
        }

        return loginUser(signUpConfirmation);
    }

    private Token loginUser(SignUpConfirmation signUpConfirmation) {
        Function<User, Token> loginFunction = new LogInPostFunction();
        return loginFunction.apply(signUpConfirmation);
    }
}
