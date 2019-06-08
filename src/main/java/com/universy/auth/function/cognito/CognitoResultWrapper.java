package com.universy.auth.function.cognito;

import com.amazonaws.AmazonWebServiceResult;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.http.SdkHttpMetadata;

import java.net.HttpURLConnection;
import java.util.Optional;

public abstract class CognitoResultWrapper<Result extends AmazonWebServiceResult<ResponseMetadata>> {

    private final Result result;

    protected CognitoResultWrapper(Result result) {
        this.result = result;
    }

    protected Result getResult(){
        return result;
    }

    public boolean wasSuccessful(){
        return Optional.ofNullable(getResult())
                .map(AmazonWebServiceResult::getSdkHttpMetadata)
                .map(SdkHttpMetadata::getHttpStatusCode)
                .map(statusCode -> statusCode.equals(HttpURLConnection.HTTP_OK))
                .orElse(Boolean.FALSE);
    }
}
