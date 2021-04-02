package com.briancollison.sbdemo.controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
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
import com.briancollison.sbdemo.model.Joke;
import com.briancollison.sbdemo.model.Widget;
import com.briancollison.sbdemo.model.WidgetDto;
import com.briancollison.sbdemo.service.JokeService;

@RestController
@RequestMapping("/widgets")
public class WidgetRest {
    WidgetRepository widgetRepository;
    JokeService jokeService;
    ModelMapper modelMapper;

    @Autowired
    public WidgetRest(WidgetRepository widgetRepository,
                      JokeService jokeService,
                      ModelMapper modelMapper) {
        this.widgetRepository = widgetRepository;
        this.jokeService = jokeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public Iterable<Widget> findAll() {
        return widgetRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Widget save(@RequestBody WidgetDto widgetDto) {
        Joke joke = jokeService.getJoke();
        if (widgetDto.getDescription() == null || widgetDto.getDescription().trim().equals("")) {
            widgetDto.setDescription(joke.getValue());
        }
        if (widgetDto.getThumbnailUrl() == null || widgetDto.getThumbnailUrl().trim().equals("")) {
            widgetDto.setThumbnailUrl(joke.getIconUrl());
        }
        Widget widget = modelMapper.map(widgetDto, Widget.class);

        return widgetRepository.save(widget);
    }

    @GetMapping("/{id}")
    public Widget findById(@PathVariable("id") long id) {
        Optional<Widget> byId = widgetRepository.findById(id);
        return byId.orElseThrow(IllegalArgumentException::new);
    }
}
