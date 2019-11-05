package app.universy.account.function.logon.exceptions;

import app.universy.lambda.exceptions.apigw.APIException;

import java.net.HttpURLConnection;

public class InvalidUserPasswordException extends APIException {

    private static final String ERROR_MESSAGE = "Password has incorrect format.";

    public InvalidUserPasswordException() {
        super(ERROR_MESSAGE, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
