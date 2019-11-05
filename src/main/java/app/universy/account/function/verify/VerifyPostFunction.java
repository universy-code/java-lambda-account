package app.universy.account.function.verify;

import app.universy.account.cognito.actions.ConfirmSignUp;
import app.universy.account.function.exceptions.UserNotFoundInPoolException;
import app.universy.account.function.login.LogInPostFunction;
import app.universy.account.function.verify.exceptions.IncorrectCodeException;
import app.universy.account.model.SignUpConfirmation;
import app.universy.account.model.Token;
import app.universy.account.model.User;
import app.universy.lambda.annotation.apigw.APIGatewayHandler;
import app.universy.lambda.annotation.apigw.APIMethod;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.CodeMismatchException;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.universy.cognito.actions.CognitoAction;

import java.util.function.Function;

@APIGatewayHandler(method = APIMethod.POST, path = "/universy/account/verify")
public class VerifyPostFunction implements Function<SignUpConfirmation, Token> {

    private final CognitoAction<SignUpConfirmation, ConfirmSignUpResult> signUpAction;
    private final Function<User, Token> loginFunction;

    public VerifyPostFunction(AWSCognitoIdentityProvider identityProvider) {
        this(new ConfirmSignUp(identityProvider),  new LogInPostFunction(identityProvider));
    }

    public VerifyPostFunction(CognitoAction<SignUpConfirmation, ConfirmSignUpResult> signUpAction,
                              Function<User, Token> loginFunction) {

        this.signUpAction = signUpAction;
        this.loginFunction = loginFunction;
    }

    @Override
    public Token apply(SignUpConfirmation signUpConfirmation) {
        try {
            signUpAction.perform(signUpConfirmation);
        } catch (CodeMismatchException e) {
            throw new IncorrectCodeException();
        } catch (UserNotFoundException e) {
            throw new UserNotFoundInPoolException(signUpConfirmation);
        }

        return loginFunction.apply(signUpConfirmation);
    }
}
