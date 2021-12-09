package com.example.wchallenge.adapter;

import com.example.wchallenge.model.Photo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PhotoRepository {
    Mono<Photo> findById(String id);

    Flux<Photo> findByAlbumId(String albumId);

    Flux<Photo> findAll();
}
