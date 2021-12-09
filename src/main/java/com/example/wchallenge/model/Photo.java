package com.example.wchallenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Photo {

    private Integer id;
    private Integer albumId;
    private String title;
    private String url;
    private String thumbnailUrl;

}
