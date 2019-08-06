package com.universy.account.function.logon.exceptions;

import com.universy.lambda.api.handlers.exceptions.BusinessException;

import java.net.HttpURLConnection;

public class InvalidUserPasswordException extends BusinessException {

    private static final String ERROR_MESSAGE = "Password has incorrect format.";

    public InvalidUserPasswordException() {
        super(ERROR_MESSAGE, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
