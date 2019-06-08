package com.universy.auth.function.cognito;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.universy.auth.model.User;
import com.universy.auth.util.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class CognitoCommand<ResultWrapper extends CognitoResultWrapper> {

    private static Logger LOGGER = LogManager.getLogger(CognitoCommand.class);

    private AWSCognitoIdentityProvider provider;

    public CognitoCommand() {
    }

    public CognitoCommand(AWSCognitoIdentityProvider provider) {
        this.provider = provider;
    }

    public ResultWrapper executeCognitoCommand(User user) {
        LOGGER.info("Executing command...");
        return performAction(user);
    }

    protected abstract ResultWrapper performAction(User user);

    protected AWSCognitoIdentityProvider getProvider() {

        if (provider == null) {
            provider = getAwsCognitoIdentityProvider();
        }
        return provider;
    }

    private AWSCognitoIdentityProvider getAwsCognitoIdentityProvider() {
        return AWSCognitoIdentityProviderClientBuilder
                .standard()
                .withRegion(Regions.fromName(Environment.getCognitoRegion()))
                .build();
    }
}
