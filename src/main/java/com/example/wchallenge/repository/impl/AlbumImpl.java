package com.example.wchallenge.repository.impl;

import com.example.wchallenge.adapter.AlbumRepository;
import com.example.wchallenge.model.Album;
import com.example.wchallenge.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AlbumImpl implements AlbumRepository {

    String baseHost = Globals.HOST;
    String baseUriAlbums = Globals.ALBUMS;
    Integer retry = Globals.RETRY;

    WebClient albumClient;

//    private final JpaAlbumRepository jpaAlbumRepository;

    public AlbumImpl(/*JpaAlbumRepository albumRepository*/) {
        /*this.jpaAlbumRepository = albumRepository;*/
        albumClient = WebClient.create(baseHost);
        albumClient = WebClient.builder()
                .baseUrl(baseHost)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

    }

    @Override
    public Mono<Album> findById(Integer albumId) {
        return albumClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUriAlbums)
                        .pathSegment(String.valueOf(albumId))
                        .build())
                .retrieve()
                .bodyToMono(Album.class)
                .retry(retry);
    }

    @Override
    public Flux<Album> findByUserId(Integer userId) {
        return albumClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUriAlbums)
                        .queryParam("userId", userId)
                        .build())
                .retrieve()
                .bodyToFlux(Album.class)
                .retry(retry);
    }

    @Override
    public Flux<Album> findAll() {
        return albumClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUriAlbums)
                        .build())
                .retrieve()
                .bodyToFlux(Album.class)
                .retry(retry);
    }

    @Override
    public Mono<Album> save(Album album) {

        Album albumRequest = Album.builder()
                .id(album.getId())
                .title(album.getTitle())
                .userId(album.getUserId())
                .build();

        return albumClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUriAlbums)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(albumRequest), Album.class))
                .retrieve()
                .bodyToMono(Album.class)
                .retry(retry);
    }

}
