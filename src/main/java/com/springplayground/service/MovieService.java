package com.springplayground.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getOmdbMovie(String queryParam) {
        return this.restTemplate.getForObject(
                "http://www.omdbapi.com/?s={query}",
                String.class,
                queryParam
        );
    }
}
