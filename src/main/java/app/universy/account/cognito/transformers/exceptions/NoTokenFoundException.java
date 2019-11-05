package app.universy.account.cognito.transformers.exceptions;

import app.universy.lambda.exceptions.apigw.APIException;

import java.net.HttpURLConnection;

public class NoTokenFoundException extends APIException {

    private static final String ERROR_MESSAGE = "No token found.";

    public NoTokenFoundException() {
        super(ERROR_MESSAGE, HttpURLConnection.HTTP_FORBIDDEN);
    }
}
