package com.example.wchallenge.controller;

import com.example.wchallenge.model.Comment;
import com.example.wchallenge.usecase.CommentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comment")
public class CommentController {

    private final CommentUseCase commentUseCase;

    @GetMapping(value = "/find-by-id")
    public Mono<Comment> findById(@RequestParam(name = "id") String id) {
        return commentUseCase.findById(id);
    }

    @GetMapping(value = "/all")
    public Flux<Comment> findAll() {
        return commentUseCase.findAll();
    }

    @GetMapping(value = "/all-by-post-id")
    public Flux<Comment> findByPostId(@RequestParam(name = "postId") String postId) {
        return commentUseCase.findByPostId(postId);
    }

    @GetMapping(value = "/find-by-name")
    public Flux<Comment> findByName(@RequestParam(name = "name") String name) {
        return commentUseCase.findByName(name);
    }
    
}
