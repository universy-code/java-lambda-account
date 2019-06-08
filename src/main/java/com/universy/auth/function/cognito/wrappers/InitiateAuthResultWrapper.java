package com.universy.auth.function.cognito.wrappers;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.universy.auth.function.cognito.CognitoResultWrapper;
import com.universy.auth.function.exceptions.authentication.NoTokenFoundException;

import java.util.Optional;

public class InitiateAuthResultWrapper extends CognitoResultWrapper<InitiateAuthResult> {

    public InitiateAuthResultWrapper(InitiateAuthResult result) {
        super(result);
    }

    public String getIdToken() {
        return Optional.ofNullable(getResult())
                .map(InitiateAuthResult::getAuthenticationResult)
                .map(AuthenticationResultType::getIdToken)
                .orElseThrow(NoTokenFoundException::new);
    }
}
