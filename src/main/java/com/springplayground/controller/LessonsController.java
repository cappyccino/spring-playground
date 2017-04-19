package com.springplayground.controller;

import com.springplayground.entity.Lesson;
import com.springplayground.repo.LessonRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonRepository repository;

    public LessonsController(LessonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<Lesson> getLessons() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Lesson getLessonById(@PathVariable("id") Long id) {
        return this.repository.findOne(id);
    }

    @PostMapping("")
    public Lesson createLesson(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }

    @PatchMapping("/{id}")
    public Lesson patchLesson(
            @PathVariable("id") Long id,
            @RequestBody Lesson lesson
            ) {
        Lesson lessonRecord = this.repository.findOne(id);
        lessonRecord.setTitle(lesson.getTitle());
        repository.save(lesson);

        return lessonRecord;
    }

    @DeleteMapping("/{id}")
    public void deleteLesson(@PathVariable("id") Long id) {
        this.repository.delete(id);
    }
}
