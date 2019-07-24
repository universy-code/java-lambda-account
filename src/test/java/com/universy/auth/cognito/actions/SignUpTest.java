package com.universy.auth.cognito.actions;

import com.amazonaws.http.SdkHttpMetadata;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminConfirmSignUpRequest;
import com.amazonaws.services.cognitoidp.model.AdminConfirmSignUpResult;
import com.universy.auth.model.Person;
import com.universy.auth.environment.Environment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;


@RunWith(PowerMockRunner.class)
@PrepareForTest(Environment.class)
@PowerMockIgnore("javax.management.*")
public class SignUpTest {

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

    private SignUp signUp;


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

        signUp = new SignUp(provider);
    }

    @Test
    public void authenticationForUser() {
/*        Person user = createUser(MAIL, PASSWORD);

        AdminConfirmSignUpResultWrapper resultWrapper = signUp.performAction(user);

        ArgumentCaptor<SignUpRequest> signUpCaptor = ArgumentCaptor.forClass(SignUpRequest.class);

        Mockito.verify(client, times(1))
                .signUp(signUpCaptor.capture());

        SignUpRequest capturedRequest = signUpCaptor.getValue();

        assertTrue(resultWrapper.wasSuccessful());
        assertEquals(CLIENT_ID, capturedRequest.getClientId());
        assertEquals(MAIL, capturedRequest.getUsername());
        assertEquals(PASSWORD, capturedRequest.getPassword());*/
    }

    private Person createUser(String mail, String password) {
        Person person = new Person();
        //person.setMail(mail);
        person.setPassword(password);
        return person;
    }


}