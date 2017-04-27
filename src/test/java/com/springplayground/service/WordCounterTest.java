package com.springplayground.service;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class WordCounterTest {
    private WordCounter wordCounter;

    @Before
    public void setUp() {
        wordCounter = new WordCounter();
    }

    @Test
    public void testCount_returnsCountCorrectly() throws Exception {
        String original = "A brown cow brown";
        Map<String, Integer> result = wordCounter.count(original);

        assertThat(result.size(), equalTo(3));
        assertThat(result.get("A"), equalTo(1));
        assertThat(result.get("brown"), equalTo(2));
        assertThat(result.get("cow"), equalTo(1));
    }

    @Test
    public void testCount_ignoresPunctuation() throws Exception {
        String original = "A cool, cow";
        Map<String, Integer> result = wordCounter.count(original);

        assertThat(result.size(), equalTo(3));
        assertThat(result.get("A"), equalTo(1));
        assertThat(result.get("cool"), equalTo(1));
        assertThat(result.get("cow"), equalTo(1));
    }
}
