package com.springplayground.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.springplayground.entity.Lesson;
import com.springplayground.repo.LessonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static java.lang.Math.toIntExact;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LessonsControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    LessonRepository lessonRepo;

    @Test
    @Transactional
    @Rollback
    public void testGetLessons() throws Exception {
        Lesson lesson1 = new Lesson();
        lesson1.setTitle("first");
        this.lessonRepo.save(lesson1);

        Lesson lesson2 = new Lesson();
        lesson2.setTitle("second");
        this.lessonRepo.save(lesson2);

        MockHttpServletRequestBuilder request = get("/lessons")
                .contentType(MediaType.APPLICATION_JSON);

        Gson builder = new GsonBuilder().create();
        String jsonString = builder.toJson(asList(lesson1, lesson2));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(jsonString));
    }

    @Test
    @Transactional
    @Rollback
    public void testGetLessonById() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setTitle("JPA");
        Lesson lesson1 = this.lessonRepo.save(lesson);

        Lesson lessonRecord = lessonRepo.findOne(lesson1.getId());
        assertThat(lessonRecord.getTitle(), equalTo("JPA"));

        MockHttpServletRequestBuilder request = get(String.format("/lessons/%s", lesson1.getId()))
                .contentType(MediaType.APPLICATION_JSON);

        Gson builder = new GsonBuilder().create();
        String jsonString = builder.toJson(lesson);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(jsonString));
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateLesson() throws Exception {
        Lesson lesson1 = new Lesson();
        lesson1.setTitle("first");
        Gson builder = new GsonBuilder().create();
        String jsonString = builder.toJson(lesson1);

        MockHttpServletRequestBuilder request = post("/lessons")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.title", is("first"))));
    }

    @Test
    @Transactional
    @Rollback
    public void testPatchLessons() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setTitle("Foobar");
        Lesson lesson1 = this.lessonRepo.save(lesson);

        Long lessonId = lesson1.getId();
        Lesson lessonRecord = lessonRepo.findOne(lessonId);
        assertThat(lessonRecord.getTitle(), equalTo("Foobar"));

        Gson builder = new GsonBuilder().create();
        lesson.setTitle("Spring Security");
        String jsonString = builder.toJson(lesson);

        RequestBuilder request = MockMvcRequestBuilders
                .patch("/lessons/" + lessonId)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(toIntExact(lessonId))))
                .andExpect(jsonPath("$.title", is("Spring Security")));

        assertThat(lessonRepo.findOne(lessonId).getTitle(), equalTo("Spring Security"));
    }


    @Test
    @Transactional
    @Rollback
    public void testDeleteLessons() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setTitle("Foobar");
        Lesson lesson1 = this.lessonRepo.save(lesson);

        Long lessonId = lesson1.getId();
        Lesson lessonRecord = lessonRepo.findOne(lessonId);
        assertThat(lessonRecord.getTitle(), equalTo("Foobar"));

        Gson builder = new GsonBuilder().create();
        String jsonString = builder.toJson(lesson);

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/lessons/" + lessonId)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString);

        mvc.perform(request)
                .andExpect(status().isOk());

        assertThat(lessonRepo.findOne(lessonId), nullValue());
    }
}