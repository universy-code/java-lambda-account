package app.universy.account.function.verify;

import app.universy.account.cognito.actions.ResendConfirmationCode;
import app.universy.account.function.exceptions.UserNotFoundInPoolException;
import app.universy.account.model.ResendConfirmation;
import app.universy.lambda.apigw.events.request.input.InputSource;
import app.universy.lambda.apigw.handler.APIHandler;
import app.universy.lambda.apigw.handler.APIMethod;
import com.amazonaws.services.cognitoidp.model.ResendConfirmationCodeResult;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.universy.cognito.actions.CognitoAction;

import java.util.function.Consumer;

@APIHandler(method = APIMethod.GET, inputSource = InputSource.QUERY_PARAMS, path = "/universy/account/verify")
public class VerifyGetConsumer implements Consumer<ResendConfirmation> {

    private final CognitoAction<ResendConfirmation, ResendConfirmationCodeResult> resendCodeAction;

    public VerifyGetConsumer() {
        this(new ResendConfirmationCode());
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