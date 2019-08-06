package com.universy.account.cognito.client;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.universy.account.environment.Environment;

public class CloudCognitoClientSupplier implements CognitoClientSupplier {

    @Override
    public AWSCognitoIdentityProvider get() {
        return AWSCognitoIdentityProviderClientBuilder
                .standard()
                .withRegion(Regions.fromName(Environment.getCognitoRegion()))
                .build();
    }
}
