package com.universy.auth.function.exceptions.authentication;

import com.universy.auth.model.User;
import com.universy.common.lambda.api.exceptions.StatusCodeTypeException;

import java.net.HttpURLConnection;

public class UserNotFoundInPoolException extends StatusCodeTypeException {

    private static final String ERROR_MESSAGE_TEMPLATE = "The user '%s' was not found in user pool.";

    public UserNotFoundInPoolException(User user){
        this(String.format(ERROR_MESSAGE_TEMPLATE, user.getMail()));
    }

    public UserNotFoundInPoolException(String message) {
        super(message, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
