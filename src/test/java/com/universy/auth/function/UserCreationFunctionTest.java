package com.universy.auth.function;

import com.amazonaws.services.cognitoidp.model.InvalidParameterException;
import com.amazonaws.services.cognitoidp.model.InvalidPasswordException;
import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.universy.auth.function.cognito.CognitoCommand;
import com.universy.auth.function.cognito.wrappers.AdminConfirmSignUpResultWrapper;
import com.universy.auth.function.exceptions.MailFormatException;
import com.universy.auth.function.exceptions.PasswordFormatException;
import com.universy.auth.function.exceptions.UnexpectedFailureException;
import com.universy.auth.function.exceptions.creation.InvalidUserPasswordException;
import com.universy.auth.function.exceptions.creation.UserAlreadyExistsException;
import com.universy.auth.function.exceptions.creation.UserNotCreatedException;
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
public class UserCreationFunctionTest {

    private static final String CORRECT_MAIL = "mail@example.com";
    private static final String INCORRECT_MAIL = "mail_example.com";
    private static final String CORRECT_PASSWORD = "1234";
    private static final String INCORRECT_PASSWORD = " ";
    private static final String TOKEN = "TOKEN";

    @Mock
    private CognitoCommand<AdminConfirmSignUpResultWrapper> signUpCommand;

    @Mock
    private Function<User, Token> authFunction;

    private Function<User, Token> createFunction;

    @Before
    public void setUp(){
        createFunction = new UserCreationFunction(signUpCommand, authFunction);
    }

    @Test
    public void userCreationHappyPath(){
        User user = createUser(CORRECT_MAIL, CORRECT_PASSWORD);
        Token token = createToken(CORRECT_MAIL, TOKEN);

        AdminConfirmSignUpResultWrapper resultWrapper = Mockito.mock(AdminConfirmSignUpResultWrapper.class);
        Mockito.when(resultWrapper.wasSuccessful()).thenReturn(Boolean.TRUE);

        Mockito.when(signUpCommand.executeCognitoCommand(any())).thenReturn(resultWrapper);
        Mockito.when(authFunction.apply(any())).thenReturn(token);

        Token returnedToken = createFunction.apply(user);

        assertEquals(token.getMail(), returnedToken.getMail());
        assertEquals(token.getToken(), returnedToken.getToken());
    }

    @Test(expected = UserNotCreatedException.class)
    public void userCreationUserNotCreatedException(){
        User user = createUser(CORRECT_MAIL, CORRECT_PASSWORD);
        Token token = createToken(CORRECT_MAIL, TOKEN);

        AdminConfirmSignUpResultWrapper resultWrapper = Mockito.mock(AdminConfirmSignUpResultWrapper.class);

        Mockito.when(resultWrapper.wasSuccessful())
                .thenReturn(Boolean.FALSE);

        Mockito.when(signUpCommand.executeCognitoCommand(any()))
                .thenReturn(resultWrapper);

        Mockito.when(authFunction.apply(any()))
                .thenReturn(token);

        createFunction.apply(user);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void userCreationUsernameExistsException(){
        User user = createUser(CORRECT_MAIL, CORRECT_PASSWORD);

        Mockito.when(signUpCommand.executeCognitoCommand(any()))
                .thenThrow(new UsernameExistsException(Strings.EMPTY));

        createFunction.apply(user);
    }

    @Test(expected = InvalidUserPasswordException.class)
    public void userCreationInvalidPasswordException(){
        User user = createUser(CORRECT_MAIL, CORRECT_PASSWORD);

        Mockito.when(signUpCommand.executeCognitoCommand(any()))
                .thenThrow(new InvalidPasswordException(Strings.EMPTY));

        createFunction.apply(user);
    }

    @Test(expected = InvalidUserPasswordException.class)
    public void userCreationInvalidParameterException(){
        User user = createUser(CORRECT_MAIL, CORRECT_PASSWORD);

        Mockito.when(signUpCommand.executeCognitoCommand(any()))
                .thenThrow(new InvalidParameterException(Strings.EMPTY));

        createFunction.apply(user);
    }

    @Test(expected = UnexpectedFailureException.class)
    public void userCreationUnexpectedException(){
        User user = createUser(CORRECT_MAIL, CORRECT_PASSWORD);

        Mockito.when(signUpCommand.executeCognitoCommand(any()))
                .thenThrow(new RuntimeException(Strings.EMPTY));

        createFunction.apply(user);
    }

    @Test(expected = MailFormatException.class)
    public void userCreationIncorrectMail(){
        User user = createUser(INCORRECT_MAIL, CORRECT_PASSWORD);
        createFunction.apply(user);
    }

    @Test(expected = PasswordFormatException.class)
    public void userCreationIncorrectPassword(){
        User user = createUser(CORRECT_MAIL, INCORRECT_PASSWORD);
        createFunction.apply(user);
    }

    private Token createToken(String mail, String token) {
        return new Token(mail, token);
    }

    private User createUser(String mail, String password) {
        User user =  new User();
        user.setMail(mail);
        user.setPassword(password);
        return user;
    }
}
