package app.universy.account.function.exceptions;

import app.universy.account.model.UsernameContainer;
import app.universy.lambda.apigw.exceptions.APIException;

import java.net.HttpURLConnection;

public class UserNotFoundInPoolException extends APIException {

    private static final String ERROR_MESSAGE_TEMPLATE = "The user '%s' was not found in user pool.";

    public UserNotFoundInPoolException(UsernameContainer usernameContainer) {
        this(String.format(ERROR_MESSAGE_TEMPLATE, usernameContainer.getUsername()));
    }

    public UserNotFoundInPoolException(String message) {
        super(message, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
