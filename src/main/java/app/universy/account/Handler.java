package app.universy.account;

import app.universy.account.function.logon.LogOnPostConsumer;
import app.universy.lambda.apigw.handler.ProxyEventHandler;
import app.universy.lambda.apigw.handler.StreamAPIGatewayHandler;
import app.universy.lambda.apigw.handler.dispatcher.DispatchStrategy;
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
                        new LogOnPostConsumer(),
                        new VerifyGetConsumer(),
                        new VerifyPostFunction()
                )
                .withDispatchStrategy(DispatchStrategy.PATH_AND_METHOD)
                .build();
    }
}
