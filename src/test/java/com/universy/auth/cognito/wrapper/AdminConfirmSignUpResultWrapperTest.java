package com.universy.auth.cognito.wrapper;

import com.amazonaws.http.SdkHttpMetadata;
import com.amazonaws.services.cognitoidp.model.AdminConfirmSignUpResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AdminConfirmSignUpResultWrapperTest {

    @Mock
    private SdkHttpMetadata sdkHttpMetadata;

    @Mock
    private AdminConfirmSignUpResult confirmSignUpResult;

    private AdminConfirmSignUpResultWrapper confirmSignUpResultWrapper;


    @Before
    public void setUp() {

        Mockito.when(confirmSignUpResult.getSdkHttpMetadata())
                .thenReturn(sdkHttpMetadata);

        confirmSignUpResultWrapper = new AdminConfirmSignUpResultWrapper(confirmSignUpResult);
    }

    @Test
    public void resultWasSuccessful() {

        Mockito.when(sdkHttpMetadata.getHttpStatusCode())
                .thenReturn(200);

        assertTrue(confirmSignUpResultWrapper.wasSuccessful());
    }

    @Test
    public void resultWasNotSuccessful() {

        Mockito.when(sdkHttpMetadata.getHttpStatusCode())
                .thenReturn(400);

        assertFalse(confirmSignUpResultWrapper.wasSuccessful());
    }
}
