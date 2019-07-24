package com.universy.auth.function.exceptions.login;

import com.universy.auth.model.SignUpConfirmation;
import com.universy.auth.model.User;
import com.universy.lambda.api.handlers.exceptions.UniversyException;

import java.net.HttpURLConnection;

public class UserNotFoundInPoolException extends UniversyException {

    private static final String ERROR_MESSAGE_TEMPLATE = "The user '%s' was not found in user pool.";

    public UserNotFoundInPoolException(User user) {
        this(String.format(ERROR_MESSAGE_TEMPLATE, user.getUsername()));
    }

    public UserNotFoundInPoolException(SignUpConfirmation signUpConfirmation) {
        this(String.format(ERROR_MESSAGE_TEMPLATE, signUpConfirmation.getUsername()));
    }

    public UserNotFoundInPoolException(String message) {
        super(message, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
