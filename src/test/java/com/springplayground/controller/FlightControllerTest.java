package com.springplayground.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.springplayground.model.Passenger;
import com.springplayground.model.Ticket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({FlightController.class})
@AutoConfigureMockMvc(secure=false)
public class FlightControllerTest {
    @Autowired
    MockMvc mockMvc;

    private Gson gson = new GsonBuilder().create();

    @Test
    public void testGetFlight() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/flights/flight")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Departs", is("2017-04-21 14:34")))
                .andExpect(jsonPath("$.TicketsWrapper[0].Passenger.FirstName", is("Some name")))
                .andExpect(jsonPath("$.TicketsWrapper[0].Passenger.LastName", is("Some other name")))
                .andExpect(jsonPath("$.TicketsWrapper[0].Price", is(200)));
    }

    @Test
    public void testGetFlights() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/flights")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].Departs", is("2017-04-21 14:34")))
                .andExpect(jsonPath("$[0].TicketsWrapper[0].Passenger.FirstName", is("Some name")))
                .andExpect(jsonPath("$[0].TicketsWrapper[0].Passenger.LastName", is("Some other name")))
                .andExpect(jsonPath("$[0].TicketsWrapper[0].Price", is(200)))
                .andExpect(jsonPath("$[1].Departs", is("2017-04-21 14:34")))
                .andExpect(jsonPath("$[1].TicketsWrapper[0].Passenger.FirstName", is("Some other name")))
                .andExpect(jsonPath("$[1].TicketsWrapper[0].Passenger.LastName", isEmptyOrNullString()))
                .andExpect(jsonPath("$[1].TicketsWrapper[0].Price", is(400)));
    }

    @Test
    public void testGetSum_withStringLiteral() throws Exception {
        String body = "{\"tickets\":[" +
                "{\"price\":200,\"passenger\":{\"firstName\":\"Some name\",\"lastName\":\"Some other name\"}}," +
                "{\"price\":150,\"passenger\":{\"firstName\":\"Name B\",\"lastName\":\"Name C\"}}" +
                "]}";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/flights/tickets/sum")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(body);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(350)));
    }

    @Test
    public void testGetSum_withSerializedGson() throws Exception {
        Passenger firstPassenger = new Passenger();
        firstPassenger.setFirstName("Some name");
        firstPassenger.setLastName("Some other name");

        Passenger secondPassenger = new Passenger();
        secondPassenger.setFirstName("Name B");
        secondPassenger.setLastName("Name C");

        Ticket firstTicket = new Ticket();
        firstTicket.setPrice(200);
        firstTicket.setPassenger(firstPassenger);

        Ticket secondTicket = new Ticket();
        secondTicket.setPrice(150);
        secondTicket.setPassenger(secondPassenger);

        JsonObject body = new JsonObject();
        JsonElement ticketListElement = gson.toJsonTree(asList(firstTicket, secondTicket));
        body.add("tickets", ticketListElement);
        Gson builder = new GsonBuilder().create();
        String jsonString = builder.toJson(body);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/flights/tickets/sum")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(350)));
    }

    @Test
    public void testGetSum_withFileFixture() throws Exception {
        URL url = this.getClass().getResource("/data.json");
        String jsonString = new String(Files.readAllBytes(Paths.get(url.getFile())));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/flights/tickets/sum")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(350)));
    }
}
