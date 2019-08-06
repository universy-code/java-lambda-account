package com.universy.account.cognito.wrappers;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.universy.account.function.login.exceptions.NoTokenFoundException;

import java.util.Optional;

public class InitiateAuthResultWrapper extends ResultWrapper<InitiateAuthResult> {

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
