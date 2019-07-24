package com.universy.auth.environment.exceptions;

public class CorruptCognitoEnvironmentSetUpException extends IllegalStateException {
    public CorruptCognitoEnvironmentSetUpException() {
        super("Environment for Cognito is not properly set.");
    }
}
