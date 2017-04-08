package com.springplayground.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Flight {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonProperty("Departs")
    Date departs;
    @JsonProperty("TicketsWrapper")
    List<Ticket> tickets;
}
