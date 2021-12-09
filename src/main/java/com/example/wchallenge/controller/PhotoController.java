package com.example.wchallenge.controller;

import com.example.wchallenge.model.Photo;
import com.example.wchallenge.usecase.PhotoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/photo")
public class PhotoController {

    private final PhotoUseCase photoUseCase;

    @GetMapping(value = "/find-by-id")
    public Mono<Photo> findById(@RequestParam(name = "id") String id) {
        return photoUseCase.findById(id);
    }

    @GetMapping(value = "/all")
    public Flux<Photo> findAll() {
        return photoUseCase.findAll();
    }

    @GetMapping(value = "/all-by-album")
    public Flux<Photo> findByAlbumId(@RequestParam(name = "albumId") String albumId) {
        return photoUseCase.findByAlbumId(albumId);
    }

    @GetMapping(value = "/all-by-user")
    public Flux<Photo> findAllByUser(@RequestParam(name = "userId") Integer userId) {
        return photoUseCase.findAllByUser(userId);
    }

}
