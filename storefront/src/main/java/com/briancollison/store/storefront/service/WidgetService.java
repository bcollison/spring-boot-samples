package com.briancollison.store.storefront.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.briancollison.store.storefront.model.Widget;
import reactor.core.publisher.Flux;

@Service
public class WidgetService {
    private final String catalogUrl;

    @Autowired
    public WidgetService(@Value("${store.catalogUrl}") String catalogUrl) {
        this.catalogUrl = catalogUrl;
    }

    public Flux<Widget> findAll() {
        return WebClient.create(catalogUrl)
                .get()
                .uri("/widgets")
                .retrieve()
                .bodyToFlux(Widget.class);
    }
}
