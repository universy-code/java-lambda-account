package app.universy.account.function.verify.exceptions;

import app.universy.lambda.exceptions.apigw.APIException;
import app.universy.lambda.exceptions.apigw.APIException;

import java.net.HttpURLConnection;

public class IncorrectCodeException extends APIException {

    private static final String ERROR_MESSAGE = "The code sent is incorrect.";

    public IncorrectCodeException() {
        super(ERROR_MESSAGE, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
