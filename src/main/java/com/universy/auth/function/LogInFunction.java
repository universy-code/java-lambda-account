package com.universy.auth.function;

import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.universy.auth.function.cognito.CognitoCommand;
import com.universy.auth.function.cognito.commands.UserAuthenticationCommand;
import com.universy.auth.function.cognito.wrappers.InitiateAuthResultWrapper;
import com.universy.auth.function.exceptions.authentication.UserNotAuthorizedException;
import com.universy.auth.function.exceptions.authentication.UserNotFoundInPoolException;
import com.universy.auth.function.validator.UserValidator;
import com.universy.auth.function.validator.Validator;
import com.universy.auth.model.Token;
import com.universy.auth.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;

public class LogInFunction implements Function<User, Token> {

    private static Logger LOGGER = LogManager.getLogger(LogInFunction.class);
    private final CognitoCommand<InitiateAuthResultWrapper> authCommand;


    public LogInFunction() {
        this(new UserAuthenticationCommand());
    }

    public LogInFunction(CognitoCommand<InitiateAuthResultWrapper> authCommand) {
        this.authCommand = authCommand;
    }

    @Override
    public Token apply(User user) {
        LOGGER.info("Received user for authentication: {}.", user.getMail());
        LOGGER.info("Starting authentication...");

        Validator userValidator = new UserValidator(user);
        userValidator.validate();

        InitiateAuthResultWrapper authResult;
        try {
            authResult = authCommand.executeCognitoCommand(user);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundInPoolException(user);
        } catch (NotAuthorizedException e) {
            throw new UserNotAuthorizedException(user);
        }
        return new Token(user.getMail(), authResult.getIdToken());
    }

}
