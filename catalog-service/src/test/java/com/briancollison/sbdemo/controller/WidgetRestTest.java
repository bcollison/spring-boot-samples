package com.briancollison.sbdemo.controller;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.briancollison.sbdemo.dao.WidgetRepository;
import com.briancollison.sbdemo.model.Joke;
import com.briancollison.sbdemo.model.Widget;
import com.briancollison.sbdemo.model.WidgetDto;
import com.briancollison.sbdemo.service.JokeService;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WidgetRestTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    JokeService jokeService;

    @MockBean
    WidgetRepository widgetRepository;

    @MockBean
    ModelMapper modelMapper;

    @Test
    void test_successful_save() throws Exception {
        Joke joke = new Joke();
        joke.setValue("i am funny");
        Widget widget = new Widget();
        widget.setName("name");
        widget.setDescription("joke");

        when(jokeService.getJoke()).thenReturn(joke);
        when(widgetRepository.save(isA(Widget.class))).thenReturn(widget);
        when(modelMapper.map(isA(WidgetDto.class), eq(Widget.class))).thenReturn(widget);
        this.mockMvc
                .perform(post("/widgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"name\"\n" +
                                "}"))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.description", is("joke")))
                .andExpect(jsonPath("$.name", is("name")));


    }
}
