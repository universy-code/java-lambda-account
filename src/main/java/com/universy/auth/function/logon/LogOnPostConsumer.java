package com.universy.auth.function.logon;

import com.amazonaws.services.cognitoidp.model.InvalidParameterException;
import com.amazonaws.services.cognitoidp.model.InvalidPasswordException;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.universy.auth.cognito.actions.CognitoAction;
import com.universy.auth.cognito.actions.SignUp;
import com.universy.auth.cognito.client.CloudCognitoClientSupplier;
import com.universy.auth.cognito.client.CognitoClientSupplier;
import com.universy.auth.cognito.wrappers.ResultWrapper;
import com.universy.auth.function.logon.exceptions.InvalidUserParametersException;
import com.universy.auth.function.logon.exceptions.InvalidUserPasswordException;
import com.universy.auth.function.logon.exceptions.UserAlreadyExistsException;
import com.universy.auth.model.Person;

import java.util.function.Consumer;

public class LogOnPostConsumer implements Consumer<Person> {

    private final CognitoAction<Person, ResultWrapper<SignUpResult>> signUpAction;

    public LogOnPostConsumer() {
        this(new CloudCognitoClientSupplier());
    }

    public LogOnPostConsumer(CognitoClientSupplier clientSupplier) {
        this(new SignUp(clientSupplier));
    }

    public LogOnPostConsumer(CognitoAction<Person, ResultWrapper<SignUpResult>> signUpAction) {
        this.signUpAction = signUpAction;
    }

    @Override
    public void accept(Person person) {

        try {

            signUpAction.perform(person);

        } catch (UsernameExistsException e) {
            throw new UserAlreadyExistsException(person);
        } catch (InvalidPasswordException e) {
            throw new InvalidUserPasswordException();
        } catch (InvalidParameterException e) {
            throw new InvalidUserParametersException();
        }
    }
}

