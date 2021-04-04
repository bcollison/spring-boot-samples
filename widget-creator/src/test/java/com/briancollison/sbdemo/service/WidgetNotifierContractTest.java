package com.briancollison.sbdemo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import au.com.dius.pact.core.model.Interaction;
import au.com.dius.pact.core.model.Pact;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;

@Provider("widget-creator")
@PactBroker(url = "http://localhost:7080")
public class WidgetNotifierContractTest {
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void testTemplate(Pact pact, Interaction interaction, PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new MessageTestTarget());
    }

    @PactVerifyProvider("a new widget message with description and thumbnailUrl")
    public String verifyMessage() {
        // TODO: replace this with the message generating code
        return "{\"name\": \"name\", \"description\":\"d\", \"thumbnailUrl\":\"tu\"}";
    }

    @PactVerifyProvider("a new widget message with no description")
    public String verifyMessage_no_description() {
        // TODO: replace this with the message generating code
        return "{\"name\": \"name\",\"thumbnailUrl\":\"tu\"}";
    }
    @PactVerifyProvider("a new widget message with no thumbnailUrl")
    public String verifyMessage_no_thumbnailUrl() {
        // TODO: replace this with the message generating code
        return "{\"name\": \"name\", \"description\":\"d\"}";
    }
}