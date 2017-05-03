package com.springplayground.controller;

import com.springplayground.entity.Movie;
import com.springplayground.repo.MovieRepository;
import com.springplayground.service.MovieService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    private final MovieRepository movieRepo;

    private MovieService movieService;

    public MoviesController(MovieRepository movieRepo, MovieService movieService) {
        this.movieService = movieService;
        this.movieRepo = movieRepo;
    }

    @GetMapping("/find/{title}")
    public Movie findByTitle(@PathVariable("title") String title) {
        return this.movieRepo.findByTitle(title);
    }

    @GetMapping("/count")
    public Integer getCount() {
        return this.movieRepo.getCount();
    }

    @GetMapping("")
    public String getMovie(@RequestParam("q") String query) {
        return movieService.getOmdbMovie(query);
    }
}