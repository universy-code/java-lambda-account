package com.universy.auth.function.cognito.wrappers;

import com.amazonaws.services.cognitoidp.model.AdminConfirmSignUpResult;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;
import com.universy.auth.function.cognito.CognitoResultWrapper;

public class AdminConfirmSignUpResultWrapper extends CognitoResultWrapper<AdminConfirmSignUpResult> {

    public AdminConfirmSignUpResultWrapper(AdminConfirmSignUpResult result) {
        super(result);
    }
}
