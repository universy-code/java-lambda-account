package com.universy.account.cognito.actions;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.universy.account.cognito.builder.CognitoAttributeBuilder;
import com.universy.account.cognito.client.CognitoClientSupplier;
import com.universy.account.cognito.wrappers.ResultWrapper;
import com.universy.account.model.Person;
import com.universy.account.environment.Environment;

public class SignUp implements CognitoAction<Person, ResultWrapper<SignUpResult>> {

    private final AWSCognitoIdentityProvider identityProvider;

    public SignUp(CognitoClientSupplier clientSupplier){
        identityProvider = clientSupplier.get();
    }

    public ResultWrapper<SignUpResult> perform(Person person) {
        SignUpRequest signUpRequest = createSignUpRequest(person);
        return ResultWrapper.wrap(identityProvider.signUp(signUpRequest));
    }

    private SignUpRequest createSignUpRequest(Person person) {
        return new SignUpRequest()
                .withClientId(Environment.getClientID())
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
