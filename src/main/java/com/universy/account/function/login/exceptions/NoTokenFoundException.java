package com.universy.account.function.login.exceptions;

import com.universy.lambda.api.handlers.exceptions.BusinessException;

import java.net.HttpURLConnection;

public class NoTokenFoundException extends BusinessException {

    private static final String ERROR_MESSAGE = "No token found.";

    public NoTokenFoundException() {
        super(ERROR_MESSAGE, HttpURLConnection.HTTP_FORBIDDEN);
    }
}
