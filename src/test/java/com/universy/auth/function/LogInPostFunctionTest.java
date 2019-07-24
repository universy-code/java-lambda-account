package com.universy.auth.function;

import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.universy.auth.cognito.wrappers.InitiateAuthResultWrapper;
import com.universy.auth.function.exceptions.MailFormatException;
import com.universy.auth.function.exceptions.PasswordFormatException;
import com.universy.auth.function.exceptions.login.UserNotAuthorizedException;
import com.universy.auth.function.exceptions.login.UserNotFoundInPoolException;
import com.universy.auth.function.login.LogInPostFunction;
import com.universy.auth.model.Person;
import com.universy.auth.model.Token;
import org.apache.logging.log4j.util.Strings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class LogInPostFunctionTest {

    private static final String CORRECT_MAIL = "mail@example.com";
    private static final String INCORRECT_MAIL = "mail_example.com";
    private static final String CORRECT_PASSWORD = "1234";
    private static final String INCORRECT_PASSWORD = " ";
    private static final String TOKEN = "TOKEN";

    @Mock
    private CognitoCommand<InitiateAuthResultWrapper> authCommand;

    private Function<Person, Token> authFunction;

    @Before
    public void setUp(){
        authFunction = new LogInPostFunction(authCommand);
    }

    @Test
    public void userAuthenticationHappyPath(){
        Person person = createUser(CORRECT_MAIL, CORRECT_PASSWORD);

        InitiateAuthResultWrapper resultWrapper = Mockito.mock(InitiateAuthResultWrapper.class);

        Mockito.when(resultWrapper.getIdToken())
                .thenReturn(TOKEN);

        Mockito.when(authCommand.executeCognitoCommand(any()))
                .thenReturn(resultWrapper);

        Token returnedToken = authFunction.apply(person);

        assertEquals(CORRECT_MAIL, returnedToken.getMail());
        assertEquals(TOKEN, returnedToken.getToken());
    }

    @Test(expected = UserNotFoundInPoolException.class)
    public void userAuthenticationUserNotFoundException(){
        Person person = createUser(CORRECT_MAIL, CORRECT_PASSWORD);

        Mockito.when(authCommand.executeCognitoCommand(any()))
                .thenThrow(new UserNotFoundException(Strings.EMPTY));

        authFunction.apply(person);
    }

    @Test(expected = UserNotAuthorizedException.class)
    public void userAuthenticationNotAuthorizedException(){
        Person person = createUser(CORRECT_MAIL, CORRECT_PASSWORD);

        Mockito.when(authCommand.executeCognitoCommand(any()))
                .thenThrow(new NotAuthorizedException(Strings.EMPTY));

        authFunction.apply(person);
    }

    @Test(expected = MailFormatException.class)
    public void userAuthenticationIncorrectMail(){
        Person person = createUser(INCORRECT_MAIL, CORRECT_PASSWORD);
        authFunction.apply(person);
    }

    @Test(expected = PasswordFormatException.class)
    public void userAuthenticationIncorrectPassword(){
        Person person = createUser(CORRECT_MAIL, INCORRECT_PASSWORD);
        authFunction.apply(person);
    }

    private Person createUser(String mail, String password) {
        Person person =  new Person();
        person.setMail(mail);
        person.setPassword(password);
        return person;
    }
}