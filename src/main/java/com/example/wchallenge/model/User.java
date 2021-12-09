package com.example.wchallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Integer id;
    private String name;
    @JsonProperty("username")
    private String userName;
    private String email;
    private String phone;
    @JsonProperty("website")
    private String webSite;
    private Address address;
    private Company company;
}
