package com.universy.account.function.exceptions;

import com.universy.lambda.api.handlers.exceptions.BusinessException;

import java.net.HttpURLConnection;

public class PasswordFormatException extends BusinessException {

    private static final String ERROR_MESSAGE_EXCEPTION = "Password is empty or has incorrect format.";

    public PasswordFormatException() {
        super(ERROR_MESSAGE_EXCEPTION, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}