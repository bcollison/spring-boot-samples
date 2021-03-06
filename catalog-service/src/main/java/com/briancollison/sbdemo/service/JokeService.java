package com.briancollison.sbdemo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.briancollison.sbdemo.model.Joke;

@Service
public class JokeService {
    private final String defaultJoke;
    private final String jokeUrl;

    public JokeService(@Value("${sbdemo.jokeUrl:https://api.chucknorris.io/jokes/random}") String jokeUrl,
                       @Value("${sbdemo.defaultJoke:Why did the chicken cross the road}") String defaultJoke) {
        this.defaultJoke = defaultJoke;
        this.jokeUrl = jokeUrl;
    }

    public Joke getJoke() {
        Joke joke;
        try {
            joke = WebClient.create()
                    .get()
                    .uri(jokeUrl)
                    .retrieve()
                    .bodyToMono(Joke.class)
                    .onErrorStop()
                    .block();
        } catch (RuntimeException e) {
            joke = new Joke();
            joke.setValue(defaultJoke);
        }
        return joke;
    }
}
