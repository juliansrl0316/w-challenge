package com.example.wchallenge.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "album_permission")
public class AlbumPermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "album_id")
    private Integer albumId;
    @Column(name = "read")
    private Boolean read;
    @Column(name = "write")
    private Boolean write;

}
