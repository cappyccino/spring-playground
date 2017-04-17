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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
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
    public void testGetLesson() throws Exception {
        MockHttpServletRequestBuilder request = get("/lessons/5")
                .contentType(MediaType.APPLICATION_JSON);

        Lesson lesson = new Lesson();
        lesson.setId(5L);
        lesson.setTitle("JPA");
        this.lessonRepo.save(lesson);

        Lesson lessonRecord = lessonRepo.findOne(5L);
        assertThat(lessonRecord.getTitle(), equalTo("JPA"));

        Gson builder = new GsonBuilder().create();
        String jsonString = builder.toJson(lesson);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(jsonString));
    }

    @Test
    @Transactional
    @Rollback
    public void testPatchLessons() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(5L);
        lesson.setTitle("Foobar");
        lessonRepo.save(lesson);

        Lesson lessonRecord = lessonRepo.findOne(5L);
        assertThat(lessonRecord.getTitle(), equalTo("Foobar"));

        String body = "{ \"id\": 5, \"title\": \"Spring Security\" }";
        RequestBuilder request = MockMvcRequestBuilders
                .patch("/lessons/5")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(body);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.title", is("Spring Security")));

        assertThat(lessonRepo.findOne(5L).getTitle(), equalTo("Spring Security"));
    }


    @Test
    @Transactional
    @Rollback
    public void testDeleteLessons() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(5L);
        lesson.setTitle("Foobar");
        lessonRepo.save(lesson);

        Lesson lessonRecord = lessonRepo.findOne(5L);
        assertThat(lessonRecord.getTitle(), equalTo("Foobar"));

        String body = "{ \"id\": 5, \"title\": \"JPA\" }";
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/lessons/5")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(body);

        mvc.perform(request)
                .andExpect(status().isOk());

        assertThat(lessonRepo.findOne(5L).getTitle(), nullValue());
    }
}