package com.example.wchallenge.controller;

import com.example.wchallenge.model.User;
import com.example.wchallenge.usecase.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserController {

    private final UserUseCase userUseCase;

    @GetMapping(value = "/find-by-id")
    public Mono<User> findById(@RequestParam(name = "id") String id) {
        return userUseCase.findById(id);
    }

    @GetMapping(value = "/all")
    public Flux<User> findAll() {
        return userUseCase.findAll();
    }

}
