package com.springplayground.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Ticket {
    @JsonProperty("Price")
    Integer price;
    @JsonProperty("Passenger")
    Passenger passenger;
}
