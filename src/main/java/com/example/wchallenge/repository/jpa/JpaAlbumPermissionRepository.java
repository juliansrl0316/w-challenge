package com.example.wchallenge.repository.jpa;

import com.example.wchallenge.repository.entity.AlbumPermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaAlbumPermissionRepository extends JpaRepository<AlbumPermissionEntity, Integer> {

    List<AlbumPermissionEntity> findByUserId(Integer userId);
    Optional<AlbumPermissionEntity> findByAlbumIdAndUserId(Integer albumId, Integer userId);
    @Modifying
    @Transactional
    Optional<Long> deleteByAlbumIdAndIdNotIn(Integer albumId, List<Integer> userIds);
    @Modifying
    @Transactional
    Optional<Long> deleteByAlbumId(Integer albumId);
    List<AlbumPermissionEntity> findByAlbumId(Integer albumId);
}
