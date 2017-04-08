package com.springplayground.model;

import lombok.Data;

import java.util.List;

@Data
public class TicketsWrapper {
    List<Ticket> tickets;
}
