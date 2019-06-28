package com.universy.auth.function.exceptions.authentication;

import com.universy.lambda.api.handlers.exceptions.UniversyException;

import java.net.HttpURLConnection;

public class NoTokenFoundException extends UniversyException {

    private static final String ERROR_MESSAGE = "No token found.";

    public NoTokenFoundException() {
        super(ERROR_MESSAGE, HttpURLConnection.HTTP_FORBIDDEN);
    }
}
