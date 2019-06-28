package com.universy.auth.function;

import com.amazonaws.services.cognitoidp.model.InvalidParameterException;
import com.amazonaws.services.cognitoidp.model.InvalidPasswordException;
import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.universy.auth.function.cognito.CognitoCommand;
import com.universy.auth.function.cognito.commands.UserSignUpCommand;
import com.universy.auth.function.cognito.wrappers.AdminConfirmSignUpResultWrapper;
import com.universy.auth.function.exceptions.creation.InvalidUserPasswordException;
import com.universy.auth.function.exceptions.creation.UserAlreadyExistsException;
import com.universy.auth.function.exceptions.creation.UserNotCreatedException;
import com.universy.auth.function.validator.UserValidator;
import com.universy.auth.function.validator.Validator;
import com.universy.auth.model.Token;
import com.universy.auth.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;

public class LogOnFunction implements Function<User, Token> {

    private static Logger LOGGER = LogManager.getLogger(LogOnFunction.class);
    private final CognitoCommand<AdminConfirmSignUpResultWrapper> signUpCommand;
    private final Function<User, Token> authFunction;

    public LogOnFunction() {
        this(new UserSignUpCommand(), new LogInFunction());
    }

    public LogOnFunction(CognitoCommand<AdminConfirmSignUpResultWrapper> command,
                         Function<User, Token> authFunction) {
        this.signUpCommand = command;
        this.authFunction = authFunction;
    }

    @Override
    public Token apply(User user) {
        LOGGER.info("Received user for creation: {}.", user.getMail());
        LOGGER.info("Starting creation...");

        Validator userValidator = new UserValidator(user);
        userValidator.validate();

        AdminConfirmSignUpResultWrapper signUpResult;
        try {

            signUpResult = signUpCommand.executeCognitoCommand(user);

        } catch (UsernameExistsException e) {
            throw new UserAlreadyExistsException(user);
        } catch (InvalidPasswordException | InvalidParameterException e) {
            throw new InvalidUserPasswordException();
        }

        if (signUpResult.wasSuccessful()) {
            return authFunction.apply(user);
        }
        throw new UserNotCreatedException(user);
    }
}

