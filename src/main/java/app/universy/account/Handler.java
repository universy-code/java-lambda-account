package app.universy.account;

import app.universy.lambda.apigw.handler.ProxyEventHandler;
import app.universy.lambda.apigw.handler.StreamAPIGatewayHandler;
import app.universy.lambda.apigw.handler.dispatcher.HandlerDispatcherBuilder;
import app.universy.account.function.login.LogInPostFunction;
import app.universy.account.function.verify.VerifyGetConsumer;
import app.universy.account.function.verify.VerifyPostFunction;

public class Handler extends StreamAPIGatewayHandler {
    @Override
    protected ProxyEventHandler getProxyEventHandler() {
        return new HandlerDispatcherBuilder()
                .withHandlers(
                        new LogInPostFunction(),
                        new LogInPostFunction(),
                        new VerifyGetConsumer(),
                        new VerifyPostFunction()
                )
                .build();
    }
}
