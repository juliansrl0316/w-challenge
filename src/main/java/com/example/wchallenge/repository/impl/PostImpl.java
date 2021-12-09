package com.example.wchallenge.repository.impl;

import com.example.wchallenge.adapter.PostRepository;
import com.example.wchallenge.model.Post;
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
public class PostImpl implements PostRepository {

    String baseHost = Globals.HOST;
    String baseUriPosts = Globals.POSTS;
    Integer retry = Globals.RETRY;

    WebClient postClient;

    public PostImpl() {
        postClient = WebClient.create(baseHost);
        postClient = WebClient.builder()
                .baseUrl(baseHost)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public Mono<Post> findById(String postId) {
        return postClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUriPosts)
                        .pathSegment(postId)
                        .build())
                .retrieve()
                .bodyToMono(Post.class)
                .retry(retry);
    }

    @Override
    public Flux<Post> findByUserId(String userId) {
        return postClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUriPosts)
                        .queryParam("userId", userId)
                        .build())
                .retrieve()
                .bodyToFlux(Post.class)
                .retry(retry);
    }

    @Override
    public Flux<Post> findAll() {
        return postClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUriPosts)
                        .build())
                .retrieve()
                .bodyToFlux(Post.class)
                .retry(retry);
    }
}
