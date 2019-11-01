package app.universy.account.function.login;

import app.universy.account.cognito.actions.InitiateAuth;
import app.universy.account.cognito.transformers.CognitoTokenTransformer;
import app.universy.account.function.exceptions.UserNotFoundInPoolException;
import app.universy.account.function.login.exceptions.UserNotAuthorizedException;
import app.universy.account.model.Token;
import app.universy.account.model.User;
import app.universy.lambda.apigw.handler.APIHandler;
import app.universy.lambda.apigw.handler.APIMethod;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.universy.cognito.actions.CognitoAction;

import java.util.function.Function;

@APIHandler(method = APIMethod.POST, path = "/universy/account/login")
public class LogInPostFunction implements Function<User, Token> {

    private final CognitoAction<User, InitiateAuthResult> initiateAuthAction;

    public LogInPostFunction() {
        this(new InitiateAuth());
    }

    public LogInPostFunction(CognitoAction<User, InitiateAuthResult> initiateAuthAction) {
        this.initiateAuthAction = initiateAuthAction;
    }

    @Override
    public Token apply(User user) {
        try {
            InitiateAuthResult authResult = initiateAuthAction.perform(user);
            CognitoTokenTransformer cognitoTokenTransformer = new CognitoTokenTransformer(user, authResult);
            return cognitoTokenTransformer.transform();
        } catch (UserNotFoundException e) {
            throw new UserNotFoundInPoolException(user);
        } catch (NotAuthorizedException e) {
            throw new UserNotAuthorizedException(user);
        }
    }
}
