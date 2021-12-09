package com.example.wchallenge.usecase;

import com.example.wchallenge.adapter.CommentRepository;
import com.example.wchallenge.model.Comment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class CommentUseCase {

    private final CommentRepository commentRepository;

    public Mono<Comment> findById(String id) {
        return commentRepository.findById(id);
    }

    public Flux<Comment> findByPostId(String postId) {
        return commentRepository.findByPostId(postId);
    }

    public Flux<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Flux<Comment> findByName(String name) {
        return commentRepository.findByName(name);
    }
}
