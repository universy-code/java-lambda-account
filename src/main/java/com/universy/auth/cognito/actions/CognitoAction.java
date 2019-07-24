package com.universy.auth.cognito.actions;

import com.universy.auth.cognito.wrappers.ResultWrapper;

public interface CognitoAction<T, R extends ResultWrapper> {
    R perform(T param);
}
