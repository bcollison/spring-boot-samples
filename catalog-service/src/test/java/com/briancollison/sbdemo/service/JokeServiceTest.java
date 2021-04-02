package com.briancollison.sbdemo.service;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.briancollison.sbdemo.model.Joke;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class JokeServiceTest {
    public static MockWebServer mockWebServer;
    private String successfulResponse = "{\n" +
            "\"categories\": [],\n" +
            "\"created_at\": \"2020-01-05 13:42:24.142371\",\n" +
            "\"icon_url\": \"https://assets.chucknorris.host/img/avatar/chuck-norris.png\",\n" +
            "\"id\": \"gaif_D9TQzCFBO7LXz2wUA\",\n" +
            "\"updated_at\": \"2020-01-05 13:42:24.142371\",\n" +
            "\"url\": \"https://api.chucknorris.io/jokes/gaif_D9TQzCFBO7LXz2wUA\",\n" +
            "\"value\": \"Chuck Norris was the reason why dinosaurs are extinct.\"\n" +
            "}";
    JokeService jokeService;

    @Test
    void test_successful_response() {
        mockWebServer.enqueue(new MockResponse()
                .setBody(successfulResponse)
                .addHeader("Content-Type", "application/json")
        );

        Joke joke = jokeService.getJoke();
        assertEquals("https://assets.chucknorris.host/img/avatar/chuck-norris.png", joke.getIconUrl());
        assertEquals("Chuck Norris was the reason why dinosaurs are extinct.", joke.getValue());
        assertEquals("gaif_D9TQzCFBO7LXz2wUA", joke.getId());
    }

    @Test
    void test_error_response() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(500)
             .setBody("error")
                .addHeader("Content-Type", "application/json")
        );
        Joke joke = jokeService.getJoke();
        assertNull(joke.getIconUrl());
        assertEquals("a horse walks into a bar", joke.getValue());
        assertNull(joke.getId());
    }

    @BeforeEach
    public void initialize() {
        String jokeServiceBaseUrl = String.format("http://localhost:%s",
                mockWebServer.getPort());
        jokeService = new JokeService(jokeServiceBaseUrl, "a horse walks into a bar");
    }

    @BeforeAll
    public static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }
}