package com.universy.auth.function.cognito.commands;

import com.amazonaws.http.SdkHttpMetadata;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminConfirmSignUpRequest;
import com.amazonaws.services.cognitoidp.model.AdminConfirmSignUpResult;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.universy.auth.function.cognito.wrappers.AdminConfirmSignUpResultWrapper;
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

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;


@RunWith(PowerMockRunner.class)
@PrepareForTest(Environment.class)
@PowerMockIgnore("javax.management.*")
public class UserSignUpCommandTest {

    private static final String MAIL = "mail@example.com";
    private static final String PASSWORD = "1234";
    private static final String CLIENT_ID = "SOME_CLIENT_ID";
    private static final String POOL_ID = "SOME_POOL_ID";


    @Mock
    private SdkHttpMetadata sdkHttpMetadata;



    @Mock
    private AWSCognitoIdentityProvider provider;

    @Mock
    private AdminConfirmSignUpResult confirmSignUpResult;

    private UserSignUpCommand signUpCommand;


    @Before
    public void setUp() {

        PowerMockito.mockStatic(Environment.class);
        PowerMockito.when(Environment.getClientID())
                .thenReturn(CLIENT_ID);

        PowerMockito.when(Environment.getUserPoolID())
                .thenReturn(POOL_ID);

        PowerMockito.when(provider.adminConfirmSignUp(any(AdminConfirmSignUpRequest.class)))
                .thenReturn(confirmSignUpResult);

        PowerMockito.when(confirmSignUpResult.getSdkHttpMetadata())
                .thenReturn(sdkHttpMetadata);

        PowerMockito.when(sdkHttpMetadata.getHttpStatusCode())
                .thenReturn(200);


        signUpCommand = new UserSignUpCommand(provider);
    }

    @Test
    public void authenticationForUser() {
        User user = createUser(MAIL, PASSWORD);

        AdminConfirmSignUpResultWrapper resultWrapper = signUpCommand.performAction(user);

        ArgumentCaptor<SignUpRequest> signUpCaptor = ArgumentCaptor.forClass(SignUpRequest.class);

        Mockito.verify(provider, times(1))
                .signUp(signUpCaptor.capture());

        SignUpRequest capturedRequest = signUpCaptor.getValue();

        assertTrue(resultWrapper.wasSuccessful());
        assertEquals(CLIENT_ID, capturedRequest.getClientId());
        assertEquals(MAIL, capturedRequest.getUsername());
        assertEquals(PASSWORD, capturedRequest.getPassword());
    }

    private User createUser(String mail, String password) {
        User user = new User();
        user.setMail(mail);
        user.setPassword(password);
        return user;
    }


}