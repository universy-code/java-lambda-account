package com.universy.auth.function.exceptions;

import com.universy.common.lambda.api.exceptions.StatusCodeTypeException;

import java.net.HttpURLConnection;

public class UnexpectedFailureException extends StatusCodeTypeException {


    private static final String ERROR_MESSAGE_TEMPLATE = "%s - %s";

    public UnexpectedFailureException(String message, Throwable throwable) {
        this(String.format(ERROR_MESSAGE_TEMPLATE, message, throwable.getClass().getName()),
                throwable,
                HttpURLConnection.HTTP_UNAUTHORIZED);
    }

    public UnexpectedFailureException(String message, Throwable throwable, int status) {
        super(message, throwable, status);
    }
}
