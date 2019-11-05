package app.universy.account.function.login.exceptions;

import app.universy.account.model.User;
import app.universy.lambda.exceptions.apigw.APIException;
import app.universy.lambda.exceptions.apigw.APIException;

import java.net.HttpURLConnection;

public class UserNotAuthorizedException extends APIException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Incorrect login data for user '%s'.";

    public UserNotAuthorizedException(User user) {
        this(String.format(ERROR_MESSAGE_TEMPLATE, user.getUsername()));
    }

    private UserNotAuthorizedException(String message) {
        super(message, HttpURLConnection.HTTP_UNAUTHORIZED);
    }
}
