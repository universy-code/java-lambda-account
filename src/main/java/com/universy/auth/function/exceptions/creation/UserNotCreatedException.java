package com.universy.auth.function.exceptions.creation;

import com.universy.auth.model.User;
import com.universy.lambda.api.handlers.exceptions.UniversyException;

import java.net.HttpURLConnection;

public class UserNotCreatedException extends UniversyException {

    private static final String ERROR_MESSAGE_TEMPLATE = "The user %s could not be created.";

    public UserNotCreatedException(User user) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, user.getMail()), HttpURLConnection.HTTP_FORBIDDEN);
    }
}
