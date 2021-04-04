package com.briancollison.sbdemo.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import com.briancollison.sbdemo.CatalogApplication;
import com.briancollison.sbdemo.dao.WidgetRepository;
import com.briancollison.sbdemo.model.Widget;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CatalogApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port=8080")
@Provider("catalog-service")
@PactBroker(url = "http://localhost:7080")
class WidgetRestContractTest {

    @MockBean
    WidgetRepository widgetRepository;

    @BeforeEach
    void setupTestTarget(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 8080, "/"));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State({"At least 5 Widgets Exist"})
    void widgetCountState() {
        List<Widget> widgetList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            widgetList.add(buildWidget(i));
        }
        when(widgetRepository.findAll()).thenReturn(widgetList);
    }

    private Widget buildWidget(int i) {
        Widget w = new Widget();
        w.setId(i);
        w.setDescription("desc: " + i);
        w.setName(("name: " + i));
        w.setThumbnailUrl("thumbnail: " + i);
        return w;
    }

}