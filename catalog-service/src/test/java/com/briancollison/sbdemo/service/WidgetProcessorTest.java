package com.briancollison.sbdemo.service;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.briancollison.sbdemo.dao.WidgetRepository;
import com.briancollison.sbdemo.model.Joke;
import com.briancollison.sbdemo.model.Widget;
import com.briancollison.sbdemo.model.WidgetDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WidgetProcessorTest {
    @Mock
    JokeService jokeService;

    @Mock
    WidgetRepository widgetRepository;

    WidgetProcessor widgetProcessor;

    @BeforeEach
    void setup() {

        widgetProcessor = new WidgetProcessor(widgetRepository, jokeService, new ModelMapper());
    }

    @Test
    void test_successful_joke() {
        Joke joke = buildTestJoke();
        when(jokeService.getJoke()).thenReturn(joke);

        Widget widget = new Widget();
        widget.setName("name");
        when(widgetRepository.save(isA(Widget.class))).thenReturn(widget);

        WidgetDto widgetDto = new WidgetDto();
        widgetDto.setName("name");

        Widget actualWidget = widgetProcessor.storeWidget(widgetDto);
        assertEquals("name", actualWidget.getName());
        assertEquals("iconUrl", actualWidget.getThumbnailUrl());
        assertEquals("i am funny", actualWidget.getDescription());
    }

    @Test
    void test_description_and_thumbnail_Provided() {
        Joke joke = buildTestJoke();
        when(jokeService.getJoke()).thenReturn(joke);

        Widget widget = new Widget();
        widget.setName("name");
        when(widgetRepository.save(isA(Widget.class))).thenReturn(widget);

        WidgetDto widgetDto = new WidgetDto();
        widgetDto.setName("name");
        widgetDto.setDescription("desc");
        widgetDto.setThumbnailUrl("thumbnailUrl");

        Widget actualWidget = widgetProcessor.storeWidget(widgetDto);
        assertEquals("name", actualWidget.getName());
        assertEquals("thumbnailUrl", actualWidget.getThumbnailUrl());
        assertEquals("desc", actualWidget.getDescription());
    }

    @NotNull
    private Joke buildTestJoke() {
        Joke joke = new Joke();
        joke.setValue("i am funny");
        joke.setIconUrl("iconUrl");
        return joke;
    }

}