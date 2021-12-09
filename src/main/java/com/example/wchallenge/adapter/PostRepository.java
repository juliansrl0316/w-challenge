package com.example.wchallenge.adapter;

import com.example.wchallenge.model.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostRepository {
    Flux<Post> findByUserId(String userId);

    Flux<Post> findAll();

    Mono<Post> findById(String postId);
}
