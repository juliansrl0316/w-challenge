package com.example.wchallenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Comment {

    private Integer id;
    private Integer postId;
    private String name;
    private String email;
    private String body;

}
