package com.briancollison.sbdemo.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briancollison.sbdemo.model.WidgetDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WidgetReceiver {
    private final WidgetProcessor widgetProcessor;

    @Autowired
    public WidgetReceiver(WidgetProcessor widgetProcessor) {
        this.widgetProcessor = widgetProcessor;
    }

    @RabbitListener(queues = "newWidget")
    public void receiveWidget(String msg) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        WidgetDto widgetDto = om.readValue(msg, WidgetDto.class);
        widgetProcessor.storeWidget(widgetDto);
    }
}
