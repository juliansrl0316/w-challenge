package com.example.wchallenge.adapter;

import com.example.wchallenge.model.AlbumPermission;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AlbumPermissionRepository {

    Mono<AlbumPermission> findById(Integer albumPermissionId);
    Flux<AlbumPermission> findByUserId(Integer userId);
    Mono<AlbumPermission> findByUserIdAndAlbumId(Integer userId, Integer albumId);
    Flux<AlbumPermission> findAll();
    Mono<List<AlbumPermission>> saveAll(List<AlbumPermission> albumPermissionList);
    Mono<AlbumPermission> save(AlbumPermission albumPermission);
    Mono<Long> deleteByAlbumIdAndIdNotIn(Integer albumId, List<Integer> userIds);
    Mono<Long> deleteByAlbumId(Integer albumId);

    Flux<AlbumPermission> findByAlbumId(Integer albumId);
}
