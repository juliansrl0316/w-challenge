package com.example.wchallenge.repository.impl;

import com.example.wchallenge.adapter.PhotoRepository;
import com.example.wchallenge.model.Photo;
import com.example.wchallenge.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class PhotoImpl implements PhotoRepository {

    String baseHost = Globals.HOST;
    String baseUriPhotos = Globals.PHOTOS;
    Integer retry = Globals.RETRY;

    WebClient photoClient;

    public PhotoImpl() {
        photoClient = WebClient.create(baseHost);
        photoClient = WebClient.builder()
                .baseUrl(baseHost)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public Mono<Photo> findById(String photoId) {
        return photoClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUriPhotos)
                        .pathSegment(photoId)
                        .build())
                .retrieve()
                .bodyToMono(Photo.class)
                .retry(retry);
    }

    @Override
    public Flux<Photo> findByAlbumId(String albumId) {
        return photoClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUriPhotos)
                        .queryParam("albumId", albumId)
                        .build())
                .retrieve()
                .bodyToFlux(Photo.class)
                .retry(retry);
    }

    @Override
    public Flux<Photo> findAll() {
        return photoClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUriPhotos)
                        .build())
                .retrieve()
                .bodyToFlux(Photo.class)
                .retry(retry);
    }
}
