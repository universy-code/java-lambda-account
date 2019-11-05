package app.universy.account;

import app.universy.account.function.login.LogInPostFunction;
import app.universy.account.function.logon.LogOnPostConsumer;
import app.universy.account.function.verify.VerifyGetConsumer;
import app.universy.account.function.verify.VerifyPostFunction;
import app.universy.lambda.StreamLambdaHandler;
import app.universy.lambda.handlers.apigw.dispatch.APIGatewayDispatchStrategy;
import app.universy.lambda.handlers.apigw.dispatch.PathAndMethodDispatchStrategy;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.universy.cognito.CognitoIdentityProviderFactory;

import java.util.List;

public class Handler extends StreamLambdaHandler {

    private AWSCognitoIdentityProvider identityProvider;

    @Override
    protected void beforeInit() {
        identityProvider = CognitoIdentityProviderFactory.createIdentityProvider();
    }

    @Override
    protected void registerHandlers(List<Object> handlers) {
        handlers.add(new LogInPostFunction(identityProvider));
        handlers.add(new LogOnPostConsumer(identityProvider));
        handlers.add(new VerifyGetConsumer(identityProvider));
        handlers.add(new VerifyPostFunction(identityProvider));
    }

    @Override
    protected APIGatewayDispatchStrategy apiGatewayDispatchStrategy() {
        return new PathAndMethodDispatchStrategy();
    }
}
