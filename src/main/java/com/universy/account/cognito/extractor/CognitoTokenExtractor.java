package com.universy.account.cognito.extractor;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.universy.account.cognito.extractor.exceptions.NoTokenFoundException;

import java.util.Optional;

public class CognitoTokenExtractor {

    private final InitiateAuthResult initiateAuthResult;

    public CognitoTokenExtractor(InitiateAuthResult initiateAuthResult) {
        this.initiateAuthResult = initiateAuthResult;
    }

    public String getIdToken() {
        return Optional.ofNullable(initiateAuthResult)
                .map(InitiateAuthResult::getAuthenticationResult)
                .map(AuthenticationResultType::getIdToken)
                .orElseThrow(NoTokenFoundException::new);
    }
}
