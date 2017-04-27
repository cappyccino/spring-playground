package com.springplayground.controller;

import com.springplayground.service.WordCounter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({WordController.class})
public class WordControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    WordCounter wordCounter;

    @Test
    public void testPostWordCount() throws Exception {
        String original = "fooey mcfoobar fooey";
        Map<String, Integer> expected = new HashMap<>();
        expected.put("fooey", 2);
        expected.put("mcfoobar", 1);
        when(wordCounter.count(anyString())).thenReturn(expected);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/words/count")
                .contentType(MediaType.TEXT_PLAIN)
                .content(original);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fooey", is(2)))
                .andExpect(jsonPath("$.mcfoobar", is(1)));
    }

}
