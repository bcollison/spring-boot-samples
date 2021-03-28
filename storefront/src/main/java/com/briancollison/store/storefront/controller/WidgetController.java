package com.briancollison.store.storefront.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.briancollison.store.storefront.service.WidgetService;

@Controller
public class WidgetController {
    WidgetService widgetService;

    @Autowired
    public WidgetController(WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    @GetMapping("/widgets")
    public String displayWidgets(Model model) {
        model.addAttribute("widgets",widgetService.findAll().toIterable());
        return "widget-list";
    }
}
