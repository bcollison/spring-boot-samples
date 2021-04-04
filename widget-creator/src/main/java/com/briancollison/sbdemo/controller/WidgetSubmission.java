package com.briancollison.sbdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.briancollison.sbdemo.domain.WidgetDto;
import com.briancollison.sbdemo.service.WidgetNotifier;

@Controller
public class WidgetSubmission {
    WidgetNotifier widgetNotifier;

    @Autowired
    public WidgetSubmission(WidgetNotifier widgetNotifier) {
        this.widgetNotifier = widgetNotifier;
    }

    @PostMapping
    public String submit() {
        WidgetDto widgetDto = new WidgetDto();
        widgetDto.setName("test");
        widgetNotifier.announceNewWidget(widgetDto);
        return "success";
    }
}
