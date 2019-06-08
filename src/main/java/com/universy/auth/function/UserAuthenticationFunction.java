package com.universy.auth.function;

import com.amazonaws.services.cognitoidp.model.*;
import com.universy.auth.function.cognito.CognitoCommand;
import com.universy.auth.function.cognito.commands.UserAuthenticationCommand;
import com.universy.auth.function.cognito.wrappers.InitiateAuthResultWrapper;
import com.universy.auth.function.exceptions.UnexpectedFailureException;
import com.universy.auth.function.exceptions.authentication.UserNotAuthorizedException;
import com.universy.auth.function.exceptions.authentication.UserNotFoundInPoolException;
import com.universy.auth.function.validator.UserValidator;
import com.universy.auth.function.validator.Validator;
import com.universy.auth.model.Token;
import com.universy.auth.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;

public class UserAuthenticationFunction implements Function<User, Token> {

    private static Logger LOGGER = LogManager.getLogger(UserAuthenticationFunction.class);
    private final CognitoCommand<InitiateAuthResultWrapper> authCommand;


    public UserAuthenticationFunction() {
        this(new UserAuthenticationCommand());
    }

    public UserAuthenticationFunction(CognitoCommand<InitiateAuthResultWrapper> authCommand) {
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
        }catch (Exception e) {
            LOGGER.error("Unexpected exception.", e);
            throw new UnexpectedFailureException("Authentication failed unexpectedly", e);
        }

        return new Token(user.getMail(), authResult.getIdToken());
    }

}
