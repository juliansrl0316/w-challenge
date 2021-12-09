package com.example.wchallenge.usecase;

import com.example.wchallenge.adapter.AlbumPermissionRepository;
import com.example.wchallenge.adapter.AlbumRepository;
import com.example.wchallenge.model.Album;
import com.example.wchallenge.model.AlbumPermission;
import com.example.wchallenge.utils.ChallengeErrorEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class AlbumUseCase {

    private final AlbumRepository albumRepository;
    private final AlbumPermissionRepository albumPermissionRepository;

    public Mono<Album> findById(Integer albumId) {
        return albumRepository.findById(albumId);
    }

    public Flux<Album> findAll() {
        return albumRepository.findAll();
    }

    public Flux<Album> findByUserId(Integer userId) {
        return Flux.merge(albumRepository.findByUserId(userId), albumPermissionRepository.findByUserId(userId)
                        .flatMap(permission -> albumRepository.findById(permission.getAlbumId())))
                .distinct();
    }

    public Mono<Album> saveAlbum(Album album) {
        return albumRepository.save(album)
                .flatMap(albumModel -> Mono.just(albumModel)
                        .map(albumData -> albumData.toBuilder()
                                .id(albumModel.getId())
                                .albumPermissionList(album.getAlbumPermissionList())
                                .build())
                        .filter(albumData -> !albumData.getAlbumPermissionList().isEmpty())
                        .flatMap(albumData -> Flux.fromIterable(albumData.getAlbumPermissionList())
                                .flatMap(albumPermissionRepository::save)
                                .then(Mono.just(albumData)))
                        .defaultIfEmpty(albumModel)
                );
    }

    public Mono<Album> editAlbum(Album album, Integer userId) {
        return albumPermissionRepository.findByUserIdAndAlbumId(userId, album.getId())
                .filter(permission -> Boolean.TRUE.equals(permission.getWrite()))
                .flatMap(permission -> albumRepository.findById(album.getId())
                        .map(albumBuilder -> albumBuilder.toBuilder()
                                .title(album.getTitle())
                                .build())
                        .flatMap(albumRepository::save)
                        .flatMap(albumModel -> saveAlbumPermissions(album, userId))
                )
                .switchIfEmpty(Mono.defer(() -> Mono.just(album)
                        .flatMap(albumData -> albumRepository.findById(albumData.getId())
                                .filter(albumModel -> albumModel.getUserId().equals(userId))
                                .map(albumBuilder -> albumBuilder.toBuilder()
                                        .title(album.getTitle())
                                        .build())
                                .flatMap(albumRepository::save))
                        .flatMap(albumModel -> saveAlbumPermissions(album, userId))
                        .switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException(ChallengeErrorEnum.YOU_CAN_NOT_MODIFY_THIS_ALBUM.name()))))));
    }

    private Mono<Album> saveAlbumPermissions(Album album, Integer userId) {
        return Mono.just(album)
                .flatMap(albumModel -> deleteOwnerAlbumFromList(userId, albumModel))
                .flatMap(albumPermissionList -> deleteUsersAlbumFromList(album).then(Mono.just(albumPermissionList)))
                .flatMapMany(albumPermissionList -> Flux.fromIterable(albumPermissionList)
                        .flatMap(albumPermission -> albumPermissionRepository.findByUserIdAndAlbumId(albumPermission.getUserId(), album.getId())
                                .map(permission -> permission.toBuilder()
                                        .read(albumPermission.getRead())
                                        .write(albumPermission.getWrite())
                                        .build())
                                .defaultIfEmpty(albumPermission)
                                .flatMap(albumPermissionRepository::save)
                                .map(permission -> album.toBuilder().albumPermissionList(albumPermissionList).build())
                        )
                )
                .then(Mono.just(album))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException(ChallengeErrorEnum.YOU_CAN_NOT_MODIFY_THIS_ALBUM.name()))));
    }

    private Mono<List<AlbumPermission>> deleteOwnerAlbumFromList(Integer userId, Album albumModel) {
        return Flux.fromIterable(albumModel.getAlbumPermissionList())
                .filter(albumPermission -> !albumPermission.getUserId().equals(userId))
                .collectList();
    }

    private Mono<Long> deleteUsersAlbumFromList(Album album) {
        List<Integer> albumIds = album.getAlbumPermissionList().stream()
                .map(AlbumPermission::getId)
                .collect(Collectors.toList());

        return (!album.getAlbumPermissionList().isEmpty()) ?
                albumPermissionRepository.deleteByAlbumIdAndIdNotIn(album.getId(), albumIds)
                : albumPermissionRepository.deleteByAlbumId(album.getId());
    }

    public Flux<AlbumPermission> findByWritePermissionsAlbum(Boolean permission, Integer albumId) {
        return albumPermissionRepository.findByAlbumId(albumId)
                .filter(albumPermission -> albumPermission.getWrite().equals(permission));
    }

    public Flux<AlbumPermission> findByReadPermissionsAlbum(Boolean permission, Integer albumId) {
        return albumPermissionRepository.findByAlbumId(albumId)
                .filter(albumPermission -> albumPermission.getRead().equals(permission));
    }
}

