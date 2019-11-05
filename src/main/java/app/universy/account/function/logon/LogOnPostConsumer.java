package app.universy.account.function.logon;

import app.universy.account.cognito.actions.SignUp;
import app.universy.account.function.logon.exceptions.InvalidUserParametersException;
import app.universy.account.function.logon.exceptions.InvalidUserPasswordException;
import app.universy.account.function.logon.exceptions.UserAlreadyExistsException;
import app.universy.account.model.Person;
import app.universy.lambda.annotation.apigw.APIGatewayHandler;
import app.universy.lambda.annotation.apigw.APIMethod;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.InvalidParameterException;
import com.amazonaws.services.cognitoidp.model.InvalidPasswordException;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.universy.cognito.actions.CognitoAction;

import java.util.function.Consumer;

@APIGatewayHandler(method = APIMethod.POST, path = "/universy/account/logon")
public class LogOnPostConsumer implements Consumer<Person> {

    private final CognitoAction<Person, SignUpResult> signUpAction;

    public LogOnPostConsumer(AWSCognitoIdentityProvider identityProvider) {
        this(new SignUp(identityProvider));
    }

    public LogOnPostConsumer(CognitoAction<Person, SignUpResult> signUpAction) {
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

