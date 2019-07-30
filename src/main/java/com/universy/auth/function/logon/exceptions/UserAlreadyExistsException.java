package com.universy.auth.function.logon.exceptions;

import com.universy.auth.model.Person;
import com.universy.lambda.api.handlers.exceptions.UniversyException;

import java.net.HttpURLConnection;

public class UserAlreadyExistsException extends UniversyException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Person '%s' already exists.";

    public UserAlreadyExistsException(Person person) {
        this(String.format(ERROR_MESSAGE_TEMPLATE, person.getUsername()));
    }

    private UserAlreadyExistsException(String message){
        super(message, HttpURLConnection.HTTP_CONFLICT);
    }
}