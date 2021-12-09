package com.example.wchallenge.usecase;

import com.example.wchallenge.adapter.UserRepository;
import com.example.wchallenge.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class UserUseCase {

    private final UserRepository userRepository;

    public Mono<User> findById(String userId){
        return userRepository.findById(userId);
    }

    public Flux<User> findAll(){
        return userRepository.findAll();
    }

}
