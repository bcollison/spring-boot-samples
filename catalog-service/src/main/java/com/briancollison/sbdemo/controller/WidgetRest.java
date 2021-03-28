package com.briancollison.sbdemo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briancollison.sbdemo.dao.WidgetRepository;
import com.briancollison.sbdemo.model.Joke;
import com.briancollison.sbdemo.model.Widget;
import com.briancollison.sbdemo.service.JokeService;

@RestController
@RequestMapping("/widgets")
public class WidgetRest {
    WidgetRepository widgetRepository;
    JokeService jokeService;

    @Autowired
    public WidgetRest(WidgetRepository widgetRepository,
                      JokeService jokeService) {
        this.widgetRepository = widgetRepository;
        this.jokeService = jokeService;
    }

    @GetMapping
    Iterable<Widget> findAll() {
        return widgetRepository.findAll();
    }

    @PostMapping
    Widget save(@RequestBody Widget widget) {
        Joke joke = jokeService.getJoke();
        if (widget.getDescription() == null || widget.getDescription().trim().equals("")) {
            widget.setDescription(joke.getValue());
        }
        if (widget.getThumbnailUrl() == null || widget.getThumbnailUrl().trim().equals("")) {
            widget.setThumbnailUrl(joke.getIconUrl());
        }

        return widgetRepository.save(widget);
    }

    @GetMapping
    @RequestMapping("/{id}")
    Widget findByProductById(@PathVariable("id") long id) {
        Optional<Widget> byId = widgetRepository.findById(id);
        return byId.orElseThrow(() -> new IllegalArgumentException());
    }
}
