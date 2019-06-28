package com.universy.auth.function.exceptions;

import com.universy.lambda.api.handlers.exceptions.UniversyException;

import java.net.HttpURLConnection;

public class PasswordFormatException extends UniversyException {

    private static final String ERROR_MESSAGE_EXCEPTION = "Password is empty or has incorrect format.";

    public PasswordFormatException() {
        super(ERROR_MESSAGE_EXCEPTION, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}