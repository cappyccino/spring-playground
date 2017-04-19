package com.springplayground.controller;

import com.springplayground.entity.Movie;
import com.springplayground.repo.MovieRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    private final MovieRepository movieRepo;

    public MoviesController(MovieRepository movieRepo) {
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
}