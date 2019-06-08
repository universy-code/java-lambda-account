package com.universy.auth.function.validator;


import com.amazonaws.services.cognitoidp.model.CreateUserPoolRequest;
import com.universy.auth.function.exceptions.MailFormatException;
import com.universy.auth.function.exceptions.PasswordFormatException;
import com.universy.auth.model.User;
import org.junit.Test;

public class UserValidatorTest {

    private static final String CORRECT_MAIL = "mail@example.com";
    private static final String INCORRECT_MAIL = "mail_example.com";
    private static final String CORRECT_PASSWORD = "example1234";
    private static final String INCORRECT_PASSWORD = " ";

    private Validator userValidator;

    @Test
    public void userValid() {

        User user = createUser(CORRECT_MAIL, CORRECT_PASSWORD);
        userValidator = new UserValidator(user);
        userValidator.validate();
    }

    @Test(expected = MailFormatException.class)
    public void mailNotValid() {

        User user = createUser(INCORRECT_MAIL, CORRECT_PASSWORD);
        userValidator = new UserValidator(user);
        userValidator.validate();
    }

    @Test(expected = PasswordFormatException.class)
    public void passwordNotValid() {

        User user = createUser(CORRECT_MAIL, INCORRECT_PASSWORD);
        userValidator = new UserValidator(user);
        userValidator.validate();
    }

    private User createUser(String mail, String password) {
        User user =  new User();
        user.setMail(mail);
        user.setPassword(password);
        return user;
    }
}
