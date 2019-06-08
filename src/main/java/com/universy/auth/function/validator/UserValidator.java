package com.universy.auth.function.validator;

import com.universy.auth.function.exceptions.MailFormatException;
import com.universy.auth.function.exceptions.PasswordFormatException;
import com.universy.auth.model.User;

public class UserValidator implements Validator {

    private static final String MAIL_FORMAT_REGEX = "[a-z0-9!_.]+@[a-zA-Z_]+?(\\.[a-zA-Z]{1,3})+";
    private final User user;

    public UserValidator(User user) {
        this.user = user;
    }

    @Override
    public void validate(){
        validateMail();
        validatePassword();
    }

    private void validateMail(){
        if(!user.getMail().matches(MAIL_FORMAT_REGEX)){
            throw new MailFormatException(user.getMail());
        }
    }

    private void validatePassword(){
        if(user.getPassword().trim().isEmpty()){
            throw new PasswordFormatException();
        }
    }
}
