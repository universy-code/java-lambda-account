package com.universy.auth.function.exceptions.authentication;

import com.universy.auth.model.User;
import com.universy.common.lambda.api.exceptions.StatusCodeTypeException;

import java.net.HttpURLConnection;

public class UserNotAuthorizedException extends StatusCodeTypeException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Incorrect authentication data for user '%s'.";

    public UserNotAuthorizedException(User user) {
        this(String.format(ERROR_MESSAGE_TEMPLATE, user.getMail()));
    }

    private UserNotAuthorizedException(String message) {
        super(message, HttpURLConnection.HTTP_UNAUTHORIZED);
    }
}
