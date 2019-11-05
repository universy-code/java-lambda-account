package app.universy.account.function.verify;

import app.universy.account.cognito.actions.ResendConfirmationCode;
import app.universy.account.function.exceptions.UserNotFoundInPoolException;
import app.universy.account.model.ResendConfirmation;
import app.universy.lambda.annotation.apigw.APIDataSource;
import app.universy.lambda.annotation.apigw.APIGatewayHandler;
import app.universy.lambda.annotation.apigw.APIMethod;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.ResendConfirmationCodeResult;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.universy.cognito.actions.CognitoAction;

import java.util.function.Consumer;

@APIGatewayHandler(method = APIMethod.GET, dataSource = APIDataSource.QUERY_PARAMS, path = "/universy/account/verify")
public class VerifyGetConsumer implements Consumer<ResendConfirmation> {

    private final CognitoAction<ResendConfirmation, ResendConfirmationCodeResult> resendCodeAction;

    public VerifyGetConsumer(AWSCognitoIdentityProvider identityProvider) {
        this(new ResendConfirmationCode(identityProvider));
    }

    public VerifyGetConsumer(CognitoAction<ResendConfirmation, ResendConfirmationCodeResult> resendCodeAction) {
        this.resendCodeAction = resendCodeAction;
    }

    @Override
    public void accept(ResendConfirmation resendConfirmation) {
        try {
            resendCodeAction.perform(resendConfirmation);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundInPoolException(resendConfirmation);
        }
    }
}