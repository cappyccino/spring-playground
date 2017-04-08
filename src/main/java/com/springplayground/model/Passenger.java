package com.springplayground.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Passenger {
    @JsonProperty("FirstName")
    String firstName;
    @JsonProperty("LastName")
    String lastName;
}
