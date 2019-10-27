package app.universy.account.function.verify;

import app.universy.account.cognito.actions.ConfirmSignUp;
import app.universy.account.function.exceptions.UserNotFoundInPoolException;
import app.universy.account.model.SignUpConfirmation;
import app.universy.account.model.Token;
import app.universy.account.model.User;
import app.universy.lambda.apigw.events.request.input.InputSource;
import app.universy.lambda.apigw.handler.APIHandler;
import app.universy.lambda.apigw.handler.APIMethod;
import com.amazonaws.services.cognitoidp.model.CodeMismatchException;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import app.universy.account.function.login.LogInPostFunction;
import app.universy.account.function.verify.exceptions.IncorrectCodeException;
import com.universy.cognito.actions.CognitoAction;

import java.util.function.Function;

@APIHandler(method = APIMethod.POST, path = "/universy/account/verify")
public class VerifyPostFunction implements Function<SignUpConfirmation, Token> {

    private final CognitoAction<SignUpConfirmation, ConfirmSignUpResult> signUpAction;

    public VerifyPostFunction() {
        this(new ConfirmSignUp());
    }

    public VerifyPostFunction(CognitoAction<SignUpConfirmation, ConfirmSignUpResult> signUpAction) {
        this.signUpAction = signUpAction;
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

        return loginUser(signUpConfirmation);
    }

    private Token loginUser(SignUpConfirmation signUpConfirmation) {
        Function<User, Token> loginFunction = new LogInPostFunction();
        return loginFunction.apply(signUpConfirmation);
    }
}
