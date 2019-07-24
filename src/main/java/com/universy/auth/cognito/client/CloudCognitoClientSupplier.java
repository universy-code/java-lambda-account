package com.universy.auth.cognito.client;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.universy.auth.environment.Environment;

public class CloudCognitoClientSupplier implements CognitoClientSupplier {

    @Override
    public AWSCognitoIdentityProvider get() {
        return AWSCognitoIdentityProviderClientBuilder
                .standard()
                .withRegion(Regions.fromName(Environment.getCognitoRegion()))
                .build();
    }
}
