package com.example.wchallenge.adapter;

import com.example.wchallenge.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Flux<User> findAll();

    Mono<User> findById(String userId);
}
