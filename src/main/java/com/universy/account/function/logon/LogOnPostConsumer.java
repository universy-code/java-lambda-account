package com.universy.account.function.logon;

import com.amazonaws.services.cognitoidp.model.InvalidParameterException;
import com.amazonaws.services.cognitoidp.model.InvalidPasswordException;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.universy.account.cognito.actions.CognitoAction;
import com.universy.account.cognito.actions.SignUp;
import com.universy.account.cognito.client.CloudCognitoClientSupplier;
import com.universy.account.cognito.client.CognitoClientSupplier;
import com.universy.account.cognito.wrappers.ResultWrapper;
import com.universy.account.function.logon.exceptions.InvalidUserParametersException;
import com.universy.account.function.logon.exceptions.InvalidUserPasswordException;
import com.universy.account.function.logon.exceptions.UserAlreadyExistsException;
import com.universy.account.model.Person;

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

