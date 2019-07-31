package com.universy.auth.function.logon.exceptions;

import com.universy.auth.model.Person;
import com.universy.lambda.api.handlers.exceptions.BusinessException;

import java.net.HttpURLConnection;

public class UserNotCreatedException extends BusinessException {

    private static final String ERROR_MESSAGE_TEMPLATE = "The user %s could not be created.";

    public UserNotCreatedException(Person person) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, person.getUsername()), HttpURLConnection.HTTP_FORBIDDEN);
    }
}
