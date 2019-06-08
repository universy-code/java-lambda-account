package com.universy.auth.function;

import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.universy.auth.function.cognito.CognitoCommand;
import com.universy.auth.function.cognito.wrappers.InitiateAuthResultWrapper;
import com.universy.auth.function.exceptions.MailFormatException;
import com.universy.auth.function.exceptions.PasswordFormatException;
import com.universy.auth.function.exceptions.UnexpectedFailureException;
import com.universy.auth.function.exceptions.authentication.UserNotAuthorizedException;
import com.universy.auth.function.exceptions.authentication.UserNotFoundInPoolException;
import com.universy.auth.model.Token;
import com.universy.auth.model.User;
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
public class UserAuthenticationFunctionTest {

    private static final String CORRECT_MAIL = "mail@example.com";
    private static final String INCORRECT_MAIL = "mail_example.com";
    private static final String CORRECT_PASSWORD = "1234";
    private static final String INCORRECT_PASSWORD = " ";
    private static final String TOKEN = "TOKEN";

    @Mock
    private CognitoCommand<InitiateAuthResultWrapper> authCommand;

    private Function<User, Token> authFunction;

    @Before
    public void setUp(){
        authFunction = new UserAuthenticationFunction(authCommand);
    }

    @Test
    public void userAuthenticationHappyPath(){
        User user = createUser(CORRECT_MAIL, CORRECT_PASSWORD);

        InitiateAuthResultWrapper resultWrapper = Mockito.mock(InitiateAuthResultWrapper.class);

        Mockito.when(resultWrapper.getIdToken())
                .thenReturn(TOKEN);

        Mockito.when(authCommand.executeCognitoCommand(any()))
                .thenReturn(resultWrapper);

        Token returnedToken = authFunction.apply(user);

        assertEquals(CORRECT_MAIL, returnedToken.getMail());
        assertEquals(TOKEN, returnedToken.getToken());
    }

    @Test(expected = UserNotFoundInPoolException.class)
    public void userAuthenticationUserNotFoundException(){
        User user = createUser(CORRECT_MAIL, CORRECT_PASSWORD);

        Mockito.when(authCommand.executeCognitoCommand(any()))
                .thenThrow(new UserNotFoundException(Strings.EMPTY));

        authFunction.apply(user);
    }

    @Test(expected = UserNotAuthorizedException.class)
    public void userAuthenticationNotAuthorizedException(){
        User user = createUser(CORRECT_MAIL, CORRECT_PASSWORD);

        Mockito.when(authCommand.executeCognitoCommand(any()))
                .thenThrow(new NotAuthorizedException(Strings.EMPTY));

        authFunction.apply(user);
    }

    @Test(expected = UnexpectedFailureException.class)
    public void userAuthenticationUnexpectedException(){
        User user = createUser(CORRECT_MAIL, CORRECT_PASSWORD);

        Mockito.when(authCommand.executeCognitoCommand(any()))
                .thenThrow(new RuntimeException(Strings.EMPTY));

        authFunction.apply(user);
    }

    @Test(expected = MailFormatException.class)
    public void userAuthenticationIncorrectMail(){
        User user = createUser(INCORRECT_MAIL, CORRECT_PASSWORD);
        authFunction.apply(user);
    }

    @Test(expected = PasswordFormatException.class)
    public void userAuthenticationIncorrectPassword(){
        User user = createUser(CORRECT_MAIL, INCORRECT_PASSWORD);
        authFunction.apply(user);
    }

    private User createUser(String mail, String password) {
        User user =  new User();
        user.setMail(mail);
        user.setPassword(password);
        return user;
    }
}