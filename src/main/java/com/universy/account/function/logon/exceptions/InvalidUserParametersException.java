package com.universy.account.function.logon.exceptions;

import com.universy.lambda.api.handlers.exceptions.BusinessException;

import java.net.HttpURLConnection;

public class InvalidUserParametersException extends BusinessException {
    private static final String ERROR_MESSAGE = "Parameters are not valid.";

    public InvalidUserParametersException() {
        super(ERROR_MESSAGE, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
