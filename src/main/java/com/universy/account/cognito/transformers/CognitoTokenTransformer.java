package com.universy.account.cognito.transformers;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.universy.account.cognito.transformers.exceptions.NoTokenFoundException;
import com.universy.account.model.Token;
import com.universy.account.model.User;

import java.util.Optional;

public class CognitoTokenTransformer {

    private final User user;
    private final InitiateAuthResult initiateAuthResult;


    public CognitoTokenTransformer(User user, InitiateAuthResult initiateAuthResult) {
        this.user = user;
        this.initiateAuthResult = initiateAuthResult;
    }

    public Token transform() {
        String username = user.getUsername();

        String idToken = Optional.ofNullable(initiateAuthResult)
                .map(InitiateAuthResult::getAuthenticationResult)
                .map(AuthenticationResultType::getIdToken)
                .orElseThrow(NoTokenFoundException::new);

        String accessToken = Optional.ofNullable(initiateAuthResult)
                .map(InitiateAuthResult::getAuthenticationResult)
                .map(AuthenticationResultType::getAccessToken)
                .orElseThrow(NoTokenFoundException::new);

        String refreshToken = Optional.ofNullable(initiateAuthResult)
                .map(InitiateAuthResult::getAuthenticationResult)
                .map(AuthenticationResultType::getIdToken)
                .orElseThrow(NoTokenFoundException::new);

        return new Token(username, idToken, accessToken, refreshToken);
    }
}
