package app.universy.account.function.logon;

import app.universy.account.cognito.actions.SignUp;
import app.universy.account.function.logon.exceptions.InvalidUserPasswordException;
import app.universy.account.function.logon.exceptions.UserAlreadyExistsException;
import app.universy.account.model.Person;
import app.universy.lambda.apigw.handler.APIHandler;
import app.universy.lambda.apigw.handler.APIMethod;
import com.amazonaws.services.cognitoidp.model.InvalidParameterException;
import com.amazonaws.services.cognitoidp.model.InvalidPasswordException;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import app.universy.account.function.logon.exceptions.InvalidUserParametersException;
import com.universy.cognito.actions.CognitoAction;

import java.util.function.Consumer;

@APIHandler(method = APIMethod.POST, path = "/universy/account/logon")
public class LogOnPostConsumer implements Consumer<Person> {

    private final CognitoAction<Person, SignUpResult> signUpAction;

    public LogOnPostConsumer(){
        this(new SignUp());
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

