package com.universy.account.function.logon.exceptions;

import com.universy.account.model.Person;
import com.universy.lambda.api.handlers.exceptions.BusinessException;

import java.net.HttpURLConnection;

public class UserAlreadyExistsException extends BusinessException {

    private static final String ERROR_MESSAGE_TEMPLATE = "User '%s' already exists.";

    public UserAlreadyExistsException(Person person) {
        this(String.format(ERROR_MESSAGE_TEMPLATE, person.getUsername()));
    }

    private UserAlreadyExistsException(String message) {
        super(message, HttpURLConnection.HTTP_CONFLICT);
    }
}
