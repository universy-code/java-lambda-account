package com.universy.auth.util.exceptions;

public class CorruptCognitoEnvironmentSetUpException extends IllegalStateException {
    public CorruptCognitoEnvironmentSetUpException() {
        super("Environment for Cognito is not properly set.");
    }
}
