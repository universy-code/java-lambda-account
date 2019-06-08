package com.universy.auth.function.exceptions.authentication;

import com.universy.common.lambda.api.exceptions.StatusCodeTypeException;

import java.net.HttpURLConnection;

public class NoTokenFoundException extends StatusCodeTypeException {

    private static final String ERROR_MESSAGE = "No token found.";

    public NoTokenFoundException() {
        super(ERROR_MESSAGE, HttpURLConnection.HTTP_FORBIDDEN);
    }
}
