package com.example.wchallenge.controller;

import com.example.wchallenge.model.Post;
import com.example.wchallenge.usecase.PostUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/post")
public class PostController {

    private final PostUseCase postUseCase;

    @GetMapping(value = "/find-by-id")
    public Mono<Post> findById(@RequestParam(name = "id") String id) {
        return postUseCase.findById(id);
    }

    @GetMapping(value = "/all")
    public Flux<Post> findAll() {
        return postUseCase.findAll();
    }

    @GetMapping(value = "/all-by-user")
    public Flux<Post> findByUserId(@RequestParam(name = "userId") String userId) {
        return postUseCase.findByUserId(userId);
    }
}
