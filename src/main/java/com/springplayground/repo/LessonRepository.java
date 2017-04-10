package com.springplayground.repo;

import com.springplayground.entity.Lesson;
import org.springframework.data.repository.CrudRepository;


public interface LessonRepository extends CrudRepository<Lesson, Long> {

}