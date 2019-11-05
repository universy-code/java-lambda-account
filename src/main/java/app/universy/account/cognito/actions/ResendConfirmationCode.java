package app.universy.account.cognito.actions;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.ResendConfirmationCodeRequest;
import com.amazonaws.services.cognitoidp.model.ResendConfirmationCodeResult;
import app.universy.account.model.ResendConfirmation;
import com.universy.cognito.CognitoIdentityProviderFactory;
import com.universy.cognito.actions.CognitoAction;
import com.universy.cognito.environment.CognitoEnvironment;

public class ResendConfirmationCode implements CognitoAction<ResendConfirmation, ResendConfirmationCodeResult> {

    private final AWSCognitoIdentityProvider identityProvider;

    public ResendConfirmationCode(AWSCognitoIdentityProvider identityProvider) {
        this.identityProvider = identityProvider;
    }

    @Override
    public ResendConfirmationCodeResult perform(ResendConfirmation resendConfirmation) {
        ResendConfirmationCodeRequest codeRequest = createResendConfirmationCode(resendConfirmation);
        return identityProvider.resendConfirmationCode(codeRequest);
    }

    private ResendConfirmationCodeRequest createResendConfirmationCode(ResendConfirmation resendConfirmation) {
        return new ResendConfirmationCodeRequest()
                .withClientId(CognitoEnvironment.getClientID())
                .withUsername(resendConfirmation.getUsername());
    }
}
