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
public class Address {

    private String street;
    private String suite;
    private String city;
    @JsonProperty("zipcode")
    private String zipCode;
    private Geo geo;

}
