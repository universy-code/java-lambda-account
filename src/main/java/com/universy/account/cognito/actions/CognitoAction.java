package com.universy.account.cognito.actions;

import com.universy.account.cognito.wrappers.ResultWrapper;

public interface CognitoAction<T, R extends ResultWrapper> {
    R perform(T param);
}
