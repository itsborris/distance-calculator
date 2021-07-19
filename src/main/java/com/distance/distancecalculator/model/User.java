package com.distance.distancecalculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @JsonProperty("id")
    long id;

    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    @JsonProperty("email")
    String email;

    @JsonProperty("ip_address")
    String ipAddress;

    @JsonProperty("latitude")
    double latitude;

    @JsonProperty("longitude")
    double longitude;

}
