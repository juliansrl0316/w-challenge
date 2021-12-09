package com.example.wchallenge.repository.impl;

import com.example.wchallenge.adapter.UserRepository;
import com.example.wchallenge.model.User;
import com.example.wchallenge.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserImpl implements UserRepository {

    String baseHost = Globals.HOST;
    String baseUriUsers = Globals.USERS;
    Integer retry = Globals.RETRY;

    WebClient userClient;

    public UserImpl() {
        userClient = WebClient.create(baseHost);
        userClient = WebClient.builder()
                .baseUrl(baseHost)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

    }

    @Override
    public Flux<User> findAll() {
        return userClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUriUsers)
                        .build())
                .retrieve()
                .bodyToFlux(User.class)
                .retry(retry);
    }

    @Override
    public Mono<User> findById(String userId) {
        return userClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUriUsers)
                        .pathSegment(userId)
                        .build())
                .retrieve()
                .bodyToMono(User.class)
                .retry(retry);
    }
}
