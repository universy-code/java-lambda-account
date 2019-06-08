package com.universy.auth.util;

import com.universy.auth.util.exceptions.CorruptCognitoEnvironmentSetUpException;

import java.util.Optional;

public class Environment {
    public static String getCognitoRegion() {
        Optional<String> awsCognitoRegion = Optional.ofNullable(System.getenv("AWS_COGNITO_REGION"));
        return awsCognitoRegion.orElseThrow(CorruptCognitoEnvironmentSetUpException::new);
    }

    public static String getClientID() {
        Optional<String> environment = Optional.ofNullable(System.getenv("CLIENT_ID"));
        return environment.orElseThrow(CorruptCognitoEnvironmentSetUpException::new);
    }

    public static String getUserPoolID() {
        Optional<String> awsRegion = Optional.ofNullable(System.getenv("POOL_ID"));
        return awsRegion.orElseThrow(CorruptCognitoEnvironmentSetUpException::new);
    }
}
