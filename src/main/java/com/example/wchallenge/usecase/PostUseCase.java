package com.example.wchallenge.usecase;

import com.example.wchallenge.adapter.PostRepository;
import com.example.wchallenge.model.Post;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class PostUseCase {

    private final PostRepository postRepository;

    public Flux<Post> findByUserId(String userId) {
        return postRepository.findByUserId(userId);
    }

    public Flux<Post> findAll() {
        return postRepository.findAll();
    }

    public Mono<Post> findById(String postId) {
        return postRepository.findById(postId);
    }
}
