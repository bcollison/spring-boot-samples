package com.briancollison.sbdemo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briancollison.sbdemo.dao.WidgetRepository;
import com.briancollison.sbdemo.model.Joke;
import com.briancollison.sbdemo.model.Widget;
import com.briancollison.sbdemo.model.WidgetDto;

@Service
public class WidgetProcessor {
    WidgetRepository widgetRepository;
    JokeService jokeService;
    ModelMapper modelMapper;

    @Autowired
    public WidgetProcessor(WidgetRepository widgetRepository, JokeService jokeService, ModelMapper modelMapper) {
        this.widgetRepository = widgetRepository;
        this.jokeService = jokeService;
        this.modelMapper = modelMapper;
    }

    public Widget storeWidget(WidgetDto widgetDto) {
        Joke joke = jokeService.getJoke();
        if (widgetDto.getDescription() == null || widgetDto.getDescription().trim().equals("")) {
            widgetDto.setDescription(joke.getValue());
        }
        if (widgetDto.getThumbnailUrl() == null || widgetDto.getThumbnailUrl().trim().equals("")) {
            widgetDto.setThumbnailUrl(joke.getIconUrl());
        }
        Widget widget = modelMapper.map(widgetDto, Widget.class);

        widgetRepository.save(widget);
        return widget;
    }
}
