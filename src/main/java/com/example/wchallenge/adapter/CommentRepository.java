package com.example.wchallenge.adapter;

import com.example.wchallenge.model.Comment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentRepository {
    Mono<Comment> findById(String id);

    Flux<Comment> findByPostId(String postId);

    Flux<Comment> findAll();

    Flux<Comment> findByName(String name);
}
