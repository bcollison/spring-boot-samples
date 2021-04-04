package com.briancollison.sbdemo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.messaging.MessagePact;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import com.fasterxml.jackson.core.JsonProcessingException;

@ExtendWith(PactConsumerTestExt.class)
@ExtendWith(MockitoExtension.class)
@PactTestFor(providerName = "widget-creator")
@PactBroker(url = "http://localhost:7080")
class WidgetReceiverPactTest {
    WidgetReceiver wc;
    @Mock
    WidgetProcessor widgetProcessor;


    @BeforeEach
    public void init() {
        wc = new WidgetReceiver(widgetProcessor);
    }

    @Test
    @PactTestFor(pactMethod = "createPact", providerType = ProviderType.ASYNCH)
    void test_receive(MessagePact messagePact) throws JsonProcessingException {
        String msg = new String(messagePact.getMessages().get(0).contentsAsBytes());
        wc.receiveWidget(msg);
    }

    @Test
    @PactTestFor(pactMethod = "createPactNoThumbnailUrl", providerType = ProviderType.ASYNCH)
    void test_receive_no_thumbnail(MessagePact messagePact) throws JsonProcessingException {
        String msg = new String(messagePact.getMessages().get(0).contentsAsBytes());
        wc.receiveWidget(msg);
    }

    @Test
    @PactTestFor(pactMethod = "createPactNoDescription", providerType = ProviderType.ASYNCH)
    void test_receive_no_desc(MessagePact messagePact) throws JsonProcessingException {
        String msg = new String(messagePact.getMessages().get(0).contentsAsBytes());
        wc.receiveWidget(msg);
    }

    @Pact(provider="widget-creator", consumer="widget-msg-receiver")
    public MessagePact createPact(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody()
                .stringType("name")
                .stringType("description")
                .stringType("thumbnailUrl")
                .asBody();

        return builder.expectsToReceive("a new widget message with description and thumbnailUrl")
                .withContent(body)
                .toPact();
    }

    @Pact(provider="widget-creator", consumer="widget-msg-receiver")
    public MessagePact createPactNoThumbnailUrl(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody()
                .stringType("name")
                .stringType("description")
                .asBody();

        return builder.expectsToReceive("a new widget message with no thumbnailUrl")
                .withContent(body)
                .toPact();
    }

    @Pact(provider="widget-creator", consumer="widget-msg-receiver")
    public MessagePact createPactNoDescription(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody()
                .stringType("name")
                .stringType("thumbnailUrl")
                .asBody();

        return builder.expectsToReceive("a new widget message with no description")
                .withContent(body)
                .toPact();
    }

}