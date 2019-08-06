package com.universy.account.function.exceptions;

import com.universy.lambda.api.handlers.exceptions.BusinessException;

import java.net.HttpURLConnection;

public class MailFormatException extends BusinessException {

    private static final String ERROR_MESSAGE_EXCEPTION = "Mail has incorrect format: %s.";

    public MailFormatException(String mail) {
        super(String.format(ERROR_MESSAGE_EXCEPTION, mail),
                HttpURLConnection.HTTP_BAD_REQUEST);
    }
}

