package com.example.wchallenge.adapter;

import com.example.wchallenge.model.Album;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlbumRepository {

    Mono<Album> findById(Integer albumId);
    Flux<Album> findByUserId(Integer userId);
    Flux<Album> findAll();
    Mono<Album> save(Album album);
//    Mono<List<Album>> saveAllAlbumsFromAPI(List<Album> albumList);

}
