package com.briancollison.sbdemo.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briancollison.sbdemo.model.WidgetDto;

@Service
public class WidgetReceiver {
    private final WidgetProcessor widgetProcessor;

    @Autowired
    public WidgetReceiver(WidgetProcessor widgetProcessor) {
        this.widgetProcessor = widgetProcessor;
    }

    @RabbitListener(messageConverter = "jsonMessageConverter", queues = "newWidget")
    public void receiveWidget(WidgetDto widgetDto) {
        widgetProcessor.storeWidget(widgetDto);
    }
}
