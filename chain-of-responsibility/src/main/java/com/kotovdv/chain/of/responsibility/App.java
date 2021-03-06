package com.kotovdv.chain.of.responsibility;

import com.kotovdv.chain.of.responsibility.handler.*;
import com.kotovdv.chain.of.responsibility.request.Credentials;
import com.kotovdv.chain.of.responsibility.request.Request;
import com.kotovdv.chain.of.responsibility.request.RequestBody;

import static com.kotovdv.chain.of.responsibility.handler.RequestHandlerPipeline.builder;
import static com.kotovdv.chain.of.responsibility.request.AuthorizedCredentials.JOHN_DOE;
import static com.kotovdv.chain.of.responsibility.request.RequestType.DATA_MODIFICATION;
import static com.kotovdv.chain.of.responsibility.request.RequestType.DATA_READ;

/**
 * @author Dmitriy Kotov
 */
public class App {

    public static void main(String[] args) {
        RequestHandler pipeline = createPipeline();

        System.out.println("**** Handling modification request ****");
        pipeline.handle(createDataModificationRequest());

        System.out.println("\n**** Handling read only request ****");
        pipeline.handle(createDataReadRequest());
    }

    private static Request createDataReadRequest() {
        RequestBody body = new RequestBody("GET PUBLIC_FIELD", true);

        return new Request(
                DATA_READ,
                new Credentials("anonymous", ""),
                body);
    }

    private static Request createDataModificationRequest() {
        RequestBody body = new RequestBody("SET MY_FIELD = 'VALUE' ", false);

        return new Request(
                DATA_MODIFICATION,
                JOHN_DOE.getCredentials(),
                body);
    }

    private static RequestHandlerPipeline createPipeline() {
        return builder()
                .add(new EncodingRequestHandler())
                .add(new AuthenticationRequestHandler())
                .add(new LoggingRequestHandler())
                .build();
    }
}