package com.briancollison.store.storefront.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactDirectory;
import com.briancollison.store.storefront.model.Widget;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "catalog-service")
class WidgetServiceContractTest {

    @Pact(provider="catalog-service", consumer="storefront")
    public RequestResponsePact testGetAll(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        DslPart body = PactDslJsonArray.arrayMinLike(5)
                .stringType("name")
                .stringType("description")
                .stringType("thumbnailUrl")
                .integerType("id")
                .asBody();


        return builder
                .given("At least 5 Widgets Exist")
                .uponReceiving("Get All Interaction")
                    .path("/widgets")
                    .method("GET")
                .willRespondWith()
                    .status(200)
                    .headers(headers)
                    .body(body)
                .toPact();
    }

    @Test
    void test(MockServer mockServer) {
        WidgetService ws = new WidgetService(mockServer.getUrl());
        List<Widget> widgets = ws.findAll().collectList().block();
        assertNotNull(widgets);
        assertTrue(widgets.size() >= 5);
    }
}