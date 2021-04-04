package com.briancollison.sbdemo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.briancollison.sbdemo.dao.WidgetRepository;
import com.briancollison.sbdemo.model.Widget;
import com.briancollison.sbdemo.model.WidgetDto;
import com.briancollison.sbdemo.service.WidgetProcessor;

@RestController
@RequestMapping("/widgets")
public class WidgetRest {
    WidgetRepository widgetRepository;
    WidgetProcessor widgetProcessor;

    @Autowired
    public WidgetRest(WidgetRepository widgetRepository,
                      WidgetProcessor widgetProcessor) {
        this.widgetRepository = widgetRepository;
        this.widgetProcessor = widgetProcessor;
    }

    @GetMapping
    public Iterable<Widget> findAll() {
        return widgetRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Widget save(@RequestBody WidgetDto widgetDto) {
        return widgetProcessor.storeWidget(widgetDto);
    }

    @GetMapping("/{id}")
    public Widget findById(@PathVariable("id") long id) {
        Optional<Widget> byId = widgetRepository.findById(id);
        return byId.orElseThrow(IllegalArgumentException::new);
    }
}
