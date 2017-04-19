package com.springplayground.repo;

import com.springplayground.entity.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    Movie findByTitle(String title);

    @Query(value = "SELECT COUNT(*) FROM movie", nativeQuery = true)
    Integer getCount();
}