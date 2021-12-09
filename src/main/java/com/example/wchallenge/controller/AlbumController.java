package com.example.wchallenge.controller;

import com.example.wchallenge.model.Album;
import com.example.wchallenge.model.AlbumPermission;
import com.example.wchallenge.usecase.AlbumUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/album")
public class AlbumController {

    private final AlbumUseCase albumUseCase;

    @GetMapping(value = "/find-by-id")
    public Mono<Album> findById(@RequestParam(name = "id") Integer id) {
        return albumUseCase.findById(id);
    }

    @GetMapping(value = "/all")
    public Flux<Album> findAll() {
        return albumUseCase.findAll();
    }

    @GetMapping(value = "/all-by-user")
    public Flux<Album> findByUserId(@RequestParam(name = "userId") Integer userId) {
        return albumUseCase.findByUserId(userId);
    }

    @GetMapping(value = "/find-by-write-album")
    public Flux<AlbumPermission> findByWritePermissionsAlbum(@RequestParam Boolean permission,
                                                      @RequestParam Integer albumId) {
        return albumUseCase.findByWritePermissionsAlbum(permission, albumId);
    }

    @GetMapping(value = "/find-by-read-album")
    public Flux<AlbumPermission> findByReadPermissionsAlbum(@RequestParam Boolean permission,
                                                        @RequestParam Integer albumId) {
        return albumUseCase.findByReadPermissionsAlbum(permission, albumId);
    }

    @PostMapping(value = "/save")
    public Mono<Album> saveAlbum(@RequestBody Album album) {
        return albumUseCase.saveAlbum(album);
    }

    @PostMapping(value = "/edit")
    public Mono<Album> editAlbum(@RequestBody Album album,
                                 @RequestParam Integer userId) {
        return albumUseCase.editAlbum(album, userId);
    }


}
