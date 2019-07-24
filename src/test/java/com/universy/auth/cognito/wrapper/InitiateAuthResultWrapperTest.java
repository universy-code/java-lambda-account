package com.universy.auth.cognito.wrapper;

import com.amazonaws.http.SdkHttpMetadata;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.universy.auth.cognito.wrappers.InitiateAuthResultWrapper;
import com.universy.auth.function.exceptions.login.NoTokenFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class InitiateAuthResultWrapperTest {

    private static final String TOKEN = "SOME_TOKEN_STRING";

    @Mock
    private SdkHttpMetadata sdkHttpMetadata;

    @Mock
    private InitiateAuthResult initiateAuthResult;

    @Mock
    private AuthenticationResultType authenticationResultType;

    private InitiateAuthResultWrapper initiateAuthResultWrapper;


    @Before
    public void setUp() {

        Mockito.when(initiateAuthResult.getAuthenticationResult())
                .thenReturn(authenticationResultType);

        Mockito.when(initiateAuthResult.getSdkHttpMetadata())
                .thenReturn(sdkHttpMetadata);

        initiateAuthResultWrapper = new InitiateAuthResultWrapper(initiateAuthResult);
    }

    @Test
    public void resultWasSuccessful() {

        Mockito.when(sdkHttpMetadata.getHttpStatusCode())
                .thenReturn(200);

        assertTrue(initiateAuthResultWrapper.wasSuccessful());
    }

    @Test
    public void resultWasNotSuccessful() {

        Mockito.when(sdkHttpMetadata.getHttpStatusCode())
                .thenReturn(400);

        assertFalse(initiateAuthResultWrapper.wasSuccessful());
    }

    @Test
    public void resultWasSuccessfulAndThereIsToken() {

        Mockito.when(sdkHttpMetadata.getHttpStatusCode())
                .thenReturn(200);

        Mockito.when(authenticationResultType.getIdToken())
                .thenReturn(TOKEN);

        assertTrue(initiateAuthResultWrapper.wasSuccessful());
        assertEquals(TOKEN, initiateAuthResultWrapper.getIdToken());
    }

    @Test(expected = NoTokenFoundException.class)
    public void resultWasSuccessfulAndThereIsNoToken() {

        Mockito.when(sdkHttpMetadata.getHttpStatusCode())
                .thenReturn(200);

        Mockito.when(authenticationResultType.getIdToken())
                .thenReturn(null);

        assertTrue(initiateAuthResultWrapper.wasSuccessful());
        initiateAuthResultWrapper.getIdToken();
    }
}
