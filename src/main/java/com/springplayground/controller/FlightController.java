package com.springplayground.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.springplayground.model.Flight;
import com.springplayground.model.Passenger;
import com.springplayground.model.Ticket;
import com.springplayground.model.TicketsWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

@RestController
public class FlightController {

    @GetMapping("/flights/flight")
    public Flight getFlight() {
        Passenger passenger = createPassenger("Some name", "Some other name");
        Ticket ticket = createTicket(passenger, 200);
        // TODO - figure out why the hours are wonky
        Date departs = new Date(2017 - 1900, Calendar.APRIL, 21, 8, 34);

        return createFlight(departs, asList(ticket));
    }

    @GetMapping("flights")
    public List<Flight> getFlights() {
        Date departs = new Date(2017 - 1900, Calendar.APRIL, 21, 8, 34);

        Passenger passenger1 = createPassenger("Some name", "Some other name");
        Ticket ticket1 = createTicket(passenger1, 200);
        Flight flight1 = createFlight(departs, asList(ticket1));

        Passenger passenger2 = createPassenger("Some other name", null);
        Ticket ticket2 = createTicket(passenger2, 400);
        Flight flight2 = createFlight(departs, asList(ticket2));

        return asList(flight1, flight2);
    }

    @PostMapping("flights/tickets/sum")
    public String postSum(@RequestBody TicketsWrapper ticketsWrapper) {
        List<Ticket> tickets = ticketsWrapper.getTickets();
        Integer sum = 0;

        System.out.println(tickets);

        for (Ticket ticket : tickets) {
            sum += ticket.getPrice();
        }

        JsonObject jsonSum = new JsonObject();
        jsonSum.addProperty("result", sum);
        Gson builder = new GsonBuilder().create();
        String jsonString = builder.toJson(jsonSum);

        return jsonString;
    }

    private Flight createFlight(Date departs, List<Ticket> tickets) {
        Flight flight = new Flight();
        flight.setDeparts(departs);
        flight.setTickets(tickets);

        return flight;
    }

    private Ticket createTicket(Passenger passenger, Integer price) {
        Ticket ticket = new Ticket();
        ticket.setPrice(price);
        ticket.setPassenger(passenger);

        return ticket;
    }

    private Passenger createPassenger(String firstName, String lastName) {
        Passenger passenger = new Passenger();
        passenger.setFirstName(firstName);
        passenger.setLastName(lastName);

        return passenger;
    }
}
