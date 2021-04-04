package com.briancollison.sbdemo.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briancollison.sbdemo.domain.WidgetDto;

@Service
public class WidgetNotifier {
    RabbitTemplate rabbitTemplate;

    @Autowired
    public WidgetNotifier(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void announceNewWidget(WidgetDto widgetDto) {
        rabbitTemplate.convertAndSend("newWidget", widgetDto);
    }
}
