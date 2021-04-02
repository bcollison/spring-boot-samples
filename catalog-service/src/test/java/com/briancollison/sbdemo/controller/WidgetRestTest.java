package com.briancollison.sbdemo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.briancollison.sbdemo.dao.WidgetRepository;
import com.briancollison.sbdemo.model.Joke;
import com.briancollison.sbdemo.model.Widget;
import com.briancollison.sbdemo.service.JokeService;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WidgetRestTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    JokeService jokeService;

    @Autowired
    WidgetRepository widgetRepository;

    @Test
    void test_succesful_get_all() throws Exception {
        for (int i = 1; i < 6; i++) {
            widgetRepository.save(buildWidget(i));
        }

        this.mockMvc
                .perform(get("/widgets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    void test_succesful_find_by_id() throws Exception {
        for (int i = 1; i < 6; i++) {
            widgetRepository.save(buildWidget(i));
        }

        this.mockMvc
                .perform(get("/widgets/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name", is("Name: 1")))
                .andExpect(jsonPath("$.description", is("Description: 1")))
                .andExpect(jsonPath("$.thumbnailUrl", is("ThumbnailUrl: 1")));
    }

    private Widget buildWidget(int i) {
        Widget w = new Widget();
        w.setId(i);
        w.setName("Name: " + i);
        w.setDescription("Description: " + i);
        w.setThumbnailUrl("ThumbnailUrl: " + i);
        return w;
    }

    @Test
    void test_successful_save() throws Exception {
        Joke joke = new Joke();
        joke.setValue("i am funny");

        when(jokeService.getJoke()).thenReturn(joke);

        this.mockMvc
                .perform(post("/widgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"name\"\n" +
                                "}"))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.description", is("i am funny")))
                .andExpect(jsonPath("$.name", is("name")));
    }

    @Test
    void test_successful_save_with_description() throws Exception {
        Joke joke = new Joke();
        joke.setValue("i am funny");

        when(jokeService.getJoke()).thenReturn(joke);

        this.mockMvc
                .perform(post("/widgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"name\", \"description\":\"desc\",\"thumbnailUrl\":\"tu\"\n" +
                                "}"))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.description", is("desc")))
                .andExpect(jsonPath("$.thumbnailUrl", is("tu")))
                .andExpect(jsonPath("$.name", is("name")));
    }

}
