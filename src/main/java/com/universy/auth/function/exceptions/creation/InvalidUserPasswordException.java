package com.universy.auth.function.exceptions.creation;

import com.universy.common.lambda.api.exceptions.StatusCodeTypeException;

import java.net.HttpURLConnection;

public class InvalidUserPasswordException extends StatusCodeTypeException {

    private static final String ERROR_MESSAGE = "Password has incorrect format.";

    public InvalidUserPasswordException() {
        super(ERROR_MESSAGE, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
