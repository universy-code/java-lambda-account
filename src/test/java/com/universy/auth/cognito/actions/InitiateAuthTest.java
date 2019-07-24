package com.universy.auth.cognito.actions;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.universy.auth.cognito.wrappers.InitiateAuthResultWrapper;
import com.universy.auth.model.Person;
import com.universy.auth.environment.Environment;
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
public class InitiateAuthTest {

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

    private InitiateAuth authenticationCommand;


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

        authenticationCommand = new InitiateAuth(provider);
    }

    @Test
    public void authenticationForUser() {
        Person person = createUser(MAIL, PASSWORD);

        InitiateAuthResultWrapper resultWrapper = authenticationCommand.performAction(person);

        ArgumentCaptor<InitiateAuthRequest> authRequestCaptor = ArgumentCaptor.forClass(InitiateAuthRequest.class);

        Mockito.verify(provider, times(1))
                .initiateAuth(authRequestCaptor.capture());

        InitiateAuthRequest capturedRequest = authRequestCaptor.getValue();

        assertEquals(TOKEN, resultWrapper.getIdToken());
        assertEquals(MAIL, capturedRequest.getAuthParameters().get(USERNAME_PARAMETER_NAME));
        assertEquals(PASSWORD, capturedRequest.getAuthParameters().get(PASSWORD_PARAMETER_NAME));
    }

    private Person createUser(String mail, String password) {
        Person person = new Person();
        //person.setMail(mail);
        person.setPassword(password);
        return person;
    }

}