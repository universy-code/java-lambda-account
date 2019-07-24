package com.universy.auth.validator;


import com.universy.auth.function.exceptions.MailFormatException;
import com.universy.auth.function.exceptions.PasswordFormatException;
import com.universy.auth.model.Person;
import org.junit.Test;

public class PersonValidatorTest {

    private static final String CORRECT_MAIL = "mail@example.com";
    private static final String INCORRECT_MAIL = "mail_example.com";
    private static final String CORRECT_PASSWORD = "example1234";
    private static final String INCORRECT_PASSWORD = " ";

    private Validator userValidator;

    @Test
    public void userValid() {

        Person person = createUser(CORRECT_MAIL, CORRECT_PASSWORD);
        userValidator = new UserValidator(person);
        userValidator.validate();
    }

    @Test(expected = MailFormatException.class)
    public void mailNotValid() {

        Person person = createUser(INCORRECT_MAIL, CORRECT_PASSWORD);
        userValidator = new UserValidator(person);
        userValidator.validate();
    }

    @Test(expected = PasswordFormatException.class)
    public void passwordNotValid() {

        Person person = createUser(CORRECT_MAIL, INCORRECT_PASSWORD);
        userValidator = new UserValidator(person);
        userValidator.validate();
    }

    private Person createUser(String mail, String password) {
        Person person =  new Person();
        person.setMail(mail);
        person.setPassword(password);
        return person;
    }
}
