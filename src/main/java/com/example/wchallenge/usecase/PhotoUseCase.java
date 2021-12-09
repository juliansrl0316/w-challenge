package com.example.wchallenge.usecase;

import com.example.wchallenge.adapter.PhotoRepository;
import com.example.wchallenge.model.Photo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class PhotoUseCase {

    private final PhotoRepository photoRepository;
    private final AlbumUseCase albumUseCase;

    public Mono<Photo> findById(String id) {
        return photoRepository.findById(id);
    }

    public Flux<Photo> findByAlbumId(String albumId) {
        return photoRepository.findByAlbumId(albumId);
    }

    public Flux<Photo> findAll() {
        return photoRepository.findAll();
    }

    public Flux<Photo> findAllByUser(Integer userId) {
        return albumUseCase.findByUserId(userId)
                .flatMap(album -> photoRepository.findByAlbumId(album.getId().toString()));
    }
}
