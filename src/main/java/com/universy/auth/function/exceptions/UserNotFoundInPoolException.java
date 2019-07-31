package com.universy.auth.function.exceptions;

import com.universy.auth.model.UsernameContainer;
import com.universy.lambda.api.handlers.exceptions.BusinessException;

import java.net.HttpURLConnection;

public class UserNotFoundInPoolException extends BusinessException {

    private static final String ERROR_MESSAGE_TEMPLATE = "The user '%s' was not found in user pool.";

    public UserNotFoundInPoolException(UsernameContainer usernameContainer) {
        this(String.format(ERROR_MESSAGE_TEMPLATE, usernameContainer.getUsername()));
    }

    public UserNotFoundInPoolException(String message) {
        super(message, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
