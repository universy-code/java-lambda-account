package com.universy.auth.function.cognito.commands;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.universy.auth.function.cognito.wrappers.InitiateAuthResultWrapper;
import com.universy.auth.model.User;
import com.universy.auth.util.Environment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Environment.class)
@PowerMockIgnore("javax.management.*")
public class UserAuthenticationCommandTest {

    private static final String USERNAME_PARAMETER_NAME = "USERNAME";
    private static final String PASSWORD_PARAMETER_NAME = "PASSWORD";
    private static final String MAIL = "mail@example.com";
    private static final String PASSWORD = "1234";
    private static final String TOKEN = "SOME_TOKEN_STRING";
    private static final String CLIENT_ID = "SOME_CLIENT_ID";

    @Mock
    private AWSCognitoIdentityProvider provider;

    @Mock
    private InitiateAuthResult authResult;

    @Mock
    private AuthenticationResultType resultType;

    private UserAuthenticationCommand authenticationCommand;


    @Before
    public void setUp() {

        PowerMockito.mockStatic(Environment.class);
        PowerMockito.when(Environment.getClientID())
                .thenReturn(CLIENT_ID);

        Mockito.when(provider.initiateAuth(any(InitiateAuthRequest.class)))
                .thenReturn(authResult);

        Mockito.when(authResult.getAuthenticationResult())
                .thenReturn(resultType);

        Mockito.when(resultType.getIdToken())
                .thenReturn(TOKEN);

        authenticationCommand = new UserAuthenticationCommand(provider);
    }

    @Test
    public void authenticationForUser() {
        User user = createUser(MAIL, PASSWORD);

        InitiateAuthResultWrapper resultWrapper = authenticationCommand.performAction(user);

        ArgumentCaptor<InitiateAuthRequest> authRequestCaptor = ArgumentCaptor.forClass(InitiateAuthRequest.class);

        Mockito.verify(provider, times(1))
                .initiateAuth(authRequestCaptor.capture());

        InitiateAuthRequest capturedRequest = authRequestCaptor.getValue();

        assertEquals(TOKEN, resultWrapper.getIdToken());
        assertEquals(MAIL, capturedRequest.getAuthParameters().get(USERNAME_PARAMETER_NAME));
        assertEquals(PASSWORD, capturedRequest.getAuthParameters().get(PASSWORD_PARAMETER_NAME));
    }

    private User createUser(String mail, String password) {
        User user = new User();
        user.setMail(mail);
        user.setPassword(password);
        return user;
    }

}