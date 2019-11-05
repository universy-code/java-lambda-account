package app.universy.account.function.logon.exceptions;

import app.universy.account.model.Person;
import app.universy.lambda.exceptions.apigw.APIException;

import java.net.HttpURLConnection;

public class UserAlreadyExistsException extends APIException {

    private static final String ERROR_MESSAGE_TEMPLATE = "User '%s' already exists.";

    public UserAlreadyExistsException(Person person) {
        this(String.format(ERROR_MESSAGE_TEMPLATE, person.getUsername()));
    }

    private UserAlreadyExistsException(String message) {
        super(message, HttpURLConnection.HTTP_CONFLICT);
    }
}
