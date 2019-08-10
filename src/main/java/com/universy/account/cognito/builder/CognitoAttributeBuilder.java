package com.universy.account.cognito.builder;

import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.universy.account.cognito.Attributes;

import java.util.ArrayList;
import java.util.List;

public class CognitoAttributeBuilder {

    private List<AttributeType> attributes;

    public CognitoAttributeBuilder() {
        attributes = new ArrayList<>();
    }

    public CognitoAttributeBuilder withEmail(String phoneNumber) {
        attributes.add(
                new AttributeType()
                        .withName(Attributes.EMAIL_ATTRIBUTE)
                        .withValue(phoneNumber)
        );
        return this;
    }

    public CognitoAttributeBuilder withGivenName(String givenName) {
        attributes.add(
                new AttributeType()
                        .withName(Attributes.GIVEN_NAME_ATTRIBUTE)
                        .withValue(givenName)
        );
        return this;
    }

    public CognitoAttributeBuilder withFamilyName(String familyName) {
        attributes.add(
                new AttributeType()
                        .withName(Attributes.FAMILY_NAME_ATTRIBUTE)
                        .withValue(familyName)
        );
        return this;
    }

    public List<AttributeType> build() {
        return attributes;
    }

}
