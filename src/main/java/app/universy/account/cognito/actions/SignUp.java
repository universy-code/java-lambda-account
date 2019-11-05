package app.universy.account.cognito.actions;

import app.universy.account.cognito.builder.CognitoAttributeBuilder;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import app.universy.account.model.Person;
import com.universy.cognito.CognitoIdentityProviderFactory;
import com.universy.cognito.actions.CognitoAction;
import com.universy.cognito.environment.CognitoEnvironment;

public class SignUp implements CognitoAction<Person, SignUpResult> {

    private final AWSCognitoIdentityProvider identityProvider;

    public SignUp(AWSCognitoIdentityProvider identityProvider) {
        this.identityProvider = identityProvider;
    }

    public SignUpResult perform(Person person) {
        SignUpRequest signUpRequest = createSignUpRequest(person);
        return identityProvider.signUp(signUpRequest);
    }

    private SignUpRequest createSignUpRequest(Person person) {
        return new SignUpRequest()
                .withClientId(CognitoEnvironment.getClientID())
                .withUsername(person.getUsername())
                .withPassword(person.getPassword())
                .withUserAttributes(
                        new CognitoAttributeBuilder()
                                .withFamilyName(person.getFamilyName())
                                .withGivenName(person.getGivenName())
                                .withEmail(person.getEmail())
                                .build()
                );
    }
}
