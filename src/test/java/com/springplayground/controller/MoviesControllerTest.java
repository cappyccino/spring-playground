package com.springplayground.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.springplayground.entity.Movie;
import com.springplayground.repo.MovieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MoviesControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    MovieRepository movieRepo;

    @Test
    @Transactional
    @Rollback
    public void testGetMovieByTitle() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("Logan");
        Movie movie1 = this.movieRepo.save(movie);

        Movie movieRecord = movieRepo.findOne(movie1.getId());
        assertThat(movieRecord.getTitle(), equalTo("Logan"));

        MockHttpServletRequestBuilder request = get("/movies/find/Logan")
                .contentType(MediaType.APPLICATION_JSON);

        Gson builder = new GsonBuilder().create();
        String jsonString = builder.toJson(movie);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(jsonString));
    }


    @Test
    @Transactional
    @Rollback
    public void testGetMovieCount() throws Exception {
        MockHttpServletRequestBuilder request = get("/movies/count")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("0"));

        Movie movie1 = new Movie();
        Movie movie2 = new Movie();
        movieRepo.save(movie1);
        movieRepo.save(movie2);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }
}