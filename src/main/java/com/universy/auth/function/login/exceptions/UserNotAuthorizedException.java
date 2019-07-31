package com.universy.auth.function.login.exceptions;

import com.universy.auth.model.User;
import com.universy.lambda.api.handlers.exceptions.BusinessException;

import java.net.HttpURLConnection;

public class UserNotAuthorizedException extends BusinessException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Incorrect login data for user '%s'.";

    public UserNotAuthorizedException(User user) {
        this(String.format(ERROR_MESSAGE_TEMPLATE, user.getUsername()));
    }

    private UserNotAuthorizedException(String message) {
        super(message, HttpURLConnection.HTTP_UNAUTHORIZED);
    }
}
