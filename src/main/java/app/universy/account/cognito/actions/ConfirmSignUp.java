package app.universy.account.cognito.actions;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;
import app.universy.account.model.SignUpConfirmation;
import com.universy.cognito.CognitoIdentityProviderFactory;
import com.universy.cognito.actions.CognitoAction;
import com.universy.cognito.environment.CognitoEnvironment;

public class ConfirmSignUp implements CognitoAction<SignUpConfirmation, ConfirmSignUpResult> {

    private final AWSCognitoIdentityProvider identityProvider;

    public ConfirmSignUp(AWSCognitoIdentityProvider identityProvider) {
        this.identityProvider = identityProvider;
    }

    @Override
    public ConfirmSignUpResult perform(SignUpConfirmation signUpConfirmation) {
        ConfirmSignUpRequest confirmSignUpRequest = createConfirmSignUpRequest(signUpConfirmation);
        return identityProvider.confirmSignUp(confirmSignUpRequest);
    }

    private ConfirmSignUpRequest createConfirmSignUpRequest(SignUpConfirmation signUpConfirmation) {
        return new ConfirmSignUpRequest()
                .withClientId(CognitoEnvironment.getClientID())
                .withConfirmationCode(signUpConfirmation.getCode())
                .withUsername(signUpConfirmation.getUsername());
    }
}
