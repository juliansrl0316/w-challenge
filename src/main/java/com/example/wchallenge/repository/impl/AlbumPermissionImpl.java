package com.example.wchallenge.repository.impl;


import com.example.wchallenge.adapter.AlbumPermissionRepository;
import com.example.wchallenge.model.AlbumPermission;
import com.example.wchallenge.repository.entity.AlbumPermissionEntity;
import com.example.wchallenge.repository.jpa.JpaAlbumPermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlbumPermissionImpl implements AlbumPermissionRepository {

    private final JpaAlbumPermissionRepository jpaAlbumPermissionRepository;

    public AlbumPermissionImpl(JpaAlbumPermissionRepository jpaAlbumPermissionRepository) {
        this.jpaAlbumPermissionRepository = jpaAlbumPermissionRepository;
    }

    @Override
    public Mono<AlbumPermission> findById(Integer albumPermissionId) {
        return Mono.justOrEmpty(jpaAlbumPermissionRepository.findById(albumPermissionId))
                .map(albumPermissionEntity -> AlbumPermission.builder()
                        .id(albumPermissionEntity.getId())
                        .userId(albumPermissionEntity.getUserId())
                        .albumId(albumPermissionEntity.getAlbumId())
                        .read(albumPermissionEntity.getRead())
                        .write(albumPermissionEntity.getWrite())
                        .build());
    }

    @Override
    public Flux<AlbumPermission> findByUserId(Integer userId) {
        return Flux.fromIterable(jpaAlbumPermissionRepository.findByUserId(userId))
                .map(albumPermissionEntity -> AlbumPermission.builder()
                        .id(albumPermissionEntity.getId())
                        .userId(albumPermissionEntity.getUserId())
                        .albumId(albumPermissionEntity.getAlbumId())
                        .read(albumPermissionEntity.getRead())
                        .write(albumPermissionEntity.getWrite())
                        .build());
    }

    @Override
    public Mono<AlbumPermission> findByUserIdAndAlbumId(Integer userId, Integer albumId) {
        return Mono.justOrEmpty(jpaAlbumPermissionRepository.findByAlbumIdAndUserId(albumId, userId))
                .map(albumPermissionEntity -> AlbumPermission.builder()
                        .id(albumPermissionEntity.getId())
                        .userId(albumPermissionEntity.getUserId())
                        .albumId(albumPermissionEntity.getAlbumId())
                        .read(albumPermissionEntity.getRead())
                        .write(albumPermissionEntity.getWrite())
                        .build());
    }

    @Override
    public Flux<AlbumPermission> findAll() {
        return Flux.fromIterable(jpaAlbumPermissionRepository.findAll())
                .map(albumPermissionEntity -> AlbumPermission.builder()
                        .id(albumPermissionEntity.getId())
                        .userId(albumPermissionEntity.getUserId())
                        .albumId(albumPermissionEntity.getAlbumId())
                        .read(albumPermissionEntity.getRead())
                        .write(albumPermissionEntity.getWrite())
                        .build());
    }

    @Override
    public Mono<List<AlbumPermission>> saveAll(List<AlbumPermission> albumPermissionList) {
        return Flux.fromIterable(albumPermissionList)
                .map(albumModel -> AlbumPermissionEntity.builder()
                        .userId(albumModel.getUserId())
                        .albumId(albumModel.getAlbumId())
                        .read(albumModel.getRead())
                        .write(albumModel.getWrite())
                        .build())
                .collectList()
                .flatMap(data -> Mono.just(jpaAlbumPermissionRepository.saveAll(data)))
                .map(albumPermissionEntities -> albumPermissionEntities.stream()
                        .map(albumPermissionEntity -> AlbumPermission.builder()
                                .albumId(albumPermissionEntity.getAlbumId())
                                .read(albumPermissionEntity.getRead())
                                .id(albumPermissionEntity.getId())
                                .userId(albumPermissionEntity.getUserId())
                                .write(albumPermissionEntity.getWrite())
                                .build())
                        .collect(Collectors.toList()));
    }

    @Override
    public Mono<AlbumPermission> save(AlbumPermission albumPermission) {
        return Mono.just(albumPermission)
                .map(albumModel -> AlbumPermissionEntity.builder()
                        .id(albumModel.getId())
                        .userId(albumModel.getUserId())
                        .albumId(albumModel.getAlbumId())
                        .read(albumModel.getRead())
                        .write(albumModel.getWrite())
                        .build())
                .flatMap(data -> Mono.just(jpaAlbumPermissionRepository.save(data)))
                .map(albumPermissionEntity -> AlbumPermission.builder()
                        .albumId(albumPermissionEntity.getAlbumId())
                        .read(albumPermissionEntity.getRead())
                        .id(albumPermissionEntity.getId())
                        .userId(albumPermissionEntity.getUserId())
                        .write(albumPermissionEntity.getWrite())
                        .build());
    }

    @Override
    public Mono<Long> deleteByAlbumIdAndIdNotIn(Integer albumId, List<Integer> userIds) {
        return Mono.justOrEmpty(jpaAlbumPermissionRepository.deleteByAlbumIdAndIdNotIn(albumId, userIds));
    }

    @Override
    public Mono<Long> deleteByAlbumId(Integer albumId) {
        return Mono.justOrEmpty(jpaAlbumPermissionRepository.deleteByAlbumId(albumId));
    }

    @Override
    public Flux<AlbumPermission> findByAlbumId(Integer albumId) {
        return Flux.fromIterable(jpaAlbumPermissionRepository.findByAlbumId(albumId))
                .map(albumPermissionEntity -> AlbumPermission.builder()
                        .id(albumPermissionEntity.getId())
                        .userId(albumPermissionEntity.getUserId())
                        .albumId(albumPermissionEntity.getAlbumId())
                        .read(albumPermissionEntity.getRead())
                        .write(albumPermissionEntity.getWrite())
                        .build());
    }
}
