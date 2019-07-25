package com.universy.auth.function.logon.exceptions;

import com.universy.lambda.api.handlers.exceptions.UniversyException;

import java.net.HttpURLConnection;

public class InvalidUserParametersException extends UniversyException {
    private static final String ERROR_MESSAGE = "Parameters are not valid.";

    public InvalidUserParametersException() {
        super(ERROR_MESSAGE, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
