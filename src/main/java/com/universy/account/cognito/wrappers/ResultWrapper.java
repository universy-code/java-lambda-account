package com.universy.account.cognito.wrappers;

import com.amazonaws.AmazonWebServiceResult;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.http.SdkHttpMetadata;

import java.net.HttpURLConnection;
import java.util.Optional;

public class ResultWrapper<R extends AmazonWebServiceResult<ResponseMetadata>> {

    private final R result;

    public static <T extends AmazonWebServiceResult<ResponseMetadata>> ResultWrapper<T> wrap(T result){
        return new ResultWrapper<>(result);
    }

    protected ResultWrapper(R result) {
        this.result = result;
    }

    protected R getResult(){
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
