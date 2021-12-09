package com.example.wchallenge.repository.impl;

import com.example.wchallenge.adapter.CommentRepository;
import com.example.wchallenge.model.Comment;
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
public class CommentImpl implements CommentRepository {

    String baseHost = Globals.HOST;
    String baseUriComments = Globals.COMMENTS;
    Integer retry = Globals.RETRY;

    WebClient commentClient;

    public CommentImpl() {
        commentClient = WebClient.create(baseHost);
        commentClient = WebClient.builder()
                .baseUrl(baseHost)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

    }

    @Override
    public Flux<Comment> findAll() {
        return commentClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUriComments)
                        .build())
                .retrieve()
                .bodyToFlux(Comment.class)
                .retry(retry);
    }

    @Override
    public Flux<Comment> findByName(String name) {
        return commentClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUriComments)
                        .queryParam("name", name)
                        .build())
                .retrieve()
                .bodyToFlux(Comment.class)
                .retry(retry);
    }

    @Override
    public Mono<Comment> findById(String commentId) {
        return commentClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUriComments)
                        .pathSegment(commentId)
                        .build())
                .retrieve()
                .bodyToMono(Comment.class)
                .retry(retry);
    }

    @Override
    public Flux<Comment> findByPostId(String postId) {

        return commentClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUriComments)
                        .queryParam("postId", postId)
                        .build())
                .retrieve()
                .bodyToFlux(Comment.class)
                .retry(retry);

    }
}
