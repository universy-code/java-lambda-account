package com.universy.auth.function.exceptions;

import com.universy.common.lambda.api.exceptions.StatusCodeTypeException;

import java.net.HttpURLConnection;

public class MailFormatException extends StatusCodeTypeException {

    private static final String ERROR_MESSAGE_EXCEPTION = "Mail has incorrect format: %s.";

    public MailFormatException(String mail) {
        super(String.format(ERROR_MESSAGE_EXCEPTION, mail),
                HttpURLConnection.HTTP_BAD_REQUEST);
    }
}

