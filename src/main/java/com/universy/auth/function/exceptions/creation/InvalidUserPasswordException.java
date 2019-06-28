package com.universy.auth.function.exceptions.creation;

import com.universy.lambda.api.handlers.exceptions.UniversyException;

import java.net.HttpURLConnection;

public class InvalidUserPasswordException extends UniversyException {

    private static final String ERROR_MESSAGE = "Password has incorrect format.";

    public InvalidUserPasswordException() {
        super(ERROR_MESSAGE, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
