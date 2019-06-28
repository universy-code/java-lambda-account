package com.universy.auth.function.exceptions.creation;

import com.universy.auth.model.User;
import com.universy.lambda.api.handlers.exceptions.UniversyException;

import java.net.HttpURLConnection;

public class UserAlreadyExistsException extends UniversyException {

    private static final String ERROR_MESSAGE_TEMPLATE = "User '%s' already exists.";

    public UserAlreadyExistsException(User user) {
        this(String.format(ERROR_MESSAGE_TEMPLATE, user.getMail()));
    }

    private UserAlreadyExistsException(String message){
        super(message, HttpURLConnection.HTTP_CONFLICT);
    }
}
