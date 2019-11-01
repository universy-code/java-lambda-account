package app.universy.account.function.logon.exceptions;

import app.universy.lambda.apigw.exceptions.APIException;

import java.net.HttpURLConnection;

public class InvalidUserParametersException extends APIException {
    private static final String ERROR_MESSAGE = "Parameters are not valid.";

    public InvalidUserParametersException() {
        super(ERROR_MESSAGE, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
