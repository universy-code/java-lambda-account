package com.universy.auth.function;

import com.amazonaws.services.cognitoidp.model.InvalidParameterException;
import com.amazonaws.services.cognitoidp.model.InvalidPasswordException;
import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.universy.auth.function.exceptions.MailFormatException;
import com.universy.auth.function.exceptions.PasswordFormatException;
import com.universy.auth.function.exceptions.logon.InvalidUserPasswordException;
import com.universy.auth.function.exceptions.logon.UserAlreadyExistsException;
import com.universy.auth.function.exceptions.logon.UserNotCreatedException;
import com.universy.auth.function.logon.LogOnPostConsumer;
import com.universy.auth.model.Person;
import com.universy.auth.model.Token;
import org.apache.logging.log4j.util.Strings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class LogOnPostConsumerTest {

    private static final String CORRECT_MAIL = "mail@example.com";
    private static final String INCORRECT_MAIL = "mail_example.com";
    private static final String CORRECT_PASSWORD = "1234";
    private static final String INCORRECT_PASSWORD = " ";
    private static final String TOKEN = "TOKEN";

    @Mock
    private CognitoCommand<SignUpResultWrapper> signUpCommand;

    private Consumer<Person> logonConsumer;

    @Before
    public void setUp(){
        logonConsumer = new LogOnPostConsumer(signUpCommand);
    }

    @Test
    public void userCreationHappyPath(){
        Person person = createUser(CORRECT_MAIL, CORRECT_PASSWORD);
        Token token = createToken(CORRECT_MAIL, TOKEN);

        SignUpResultWrapper resultWrapper = Mockito.mock(SignUpResultWrapper.class);
        Mockito.when(resultWrapper.wasSuccessful()).thenReturn(Boolean.TRUE);

        Mockito.when(signUpCommand.executeCognitoCommand(any())).thenReturn(resultWrapper);

        logonConsumer.accept(person);
    }

    @Test(expected = UserNotCreatedException.class)
    public void userCreationUserNotCreatedException(){
        Person person = createUser(CORRECT_MAIL, CORRECT_PASSWORD);
        Token token = createToken(CORRECT_MAIL, TOKEN);

        SignUpResultWrapper resultWrapper = Mockito.mock(SignUpResultWrapper.class);
        Mockito.when(resultWrapper.wasSuccessful()).thenReturn(Boolean.TRUE);

        Mockito.when(resultWrapper.wasSuccessful())
                .thenReturn(Boolean.FALSE);

        Mockito.when(signUpCommand.executeCognitoCommand(any()))
                .thenReturn(resultWrapper);

        logonConsumer.accept(person);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void userCreationUsernameExistsException(){
        Person person = createUser(CORRECT_MAIL, CORRECT_PASSWORD);

        Mockito.when(signUpCommand.executeCognitoCommand(any()))
                .thenThrow(new UsernameExistsException(Strings.EMPTY));

        logonConsumer.accept(person);
    }

    @Test(expected = InvalidUserPasswordException.class)
    public void userCreationInvalidPasswordException(){
        Person person = createUser(CORRECT_MAIL, CORRECT_PASSWORD);

        Mockito.when(signUpCommand.executeCognitoCommand(any()))
                .thenThrow(new InvalidPasswordException(Strings.EMPTY));

        logonConsumer.accept(person);
    }

    @Test(expected = InvalidUserPasswordException.class)
    public void userCreationInvalidParameterException(){
        Person person = createUser(CORRECT_MAIL, CORRECT_PASSWORD);

        Mockito.when(signUpCommand.executeCognitoCommand(any()))
                .thenThrow(new InvalidParameterException(Strings.EMPTY));

        logonConsumer.accept(person);
    }

    @Test(expected = MailFormatException.class)
    public void userCreationIncorrectMail(){
        Person person = createUser(INCORRECT_MAIL, CORRECT_PASSWORD);
        logonConsumer.accept(person);
    }

    @Test(expected = PasswordFormatException.class)
    public void userCreationIncorrectPassword(){
        Person person = createUser(CORRECT_MAIL, INCORRECT_PASSWORD);
        logonConsumer.accept(person);
    }

    private Token createToken(String mail, String token) {
        return new Token(mail, token);
    }

    private Person createUser(String mail, String password) {
        Person person =  new Person();
        //person.setMail(mail);
        person.setPassword(password);
        return person;
    }
}
