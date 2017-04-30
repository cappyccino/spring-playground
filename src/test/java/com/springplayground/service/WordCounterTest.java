package com.springplayground.service;

import com.springplayground.configuration.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WordCounterTest {

    private WordCounter wordCounter;

    private Configuration configuration;

    @Before
    public void setUp() {
        configuration = mock(Configuration.class);
        wordCounter = new WordCounter(configuration);
    }

    @Test
    public void testCount_returnsCountCorrectly() throws Exception {
        when(configuration.getWordsSkip()).thenReturn(emptyList());
        String original = "a brown cow brown";
        Map<String, Integer> result = wordCounter.count(original);

        assertThat(result.size(), equalTo(3));
        assertThat(result.get("a"), equalTo(1));
        assertThat(result.get("brown"), equalTo(2));
        assertThat(result.get("cow"), equalTo(1));
    }

    @Test
    public void testCount_ignoresPunctuation() throws Exception {
        when(configuration.getWordsSkip()).thenReturn(emptyList());
        String original = "a cool, cow!";
        Map<String, Integer> result = wordCounter.count(original);

        assertThat(result.size(), equalTo(3));
        assertThat(result.get("a"), equalTo(1));
        assertThat(result.get("cool"), equalTo(1));
        assertThat(result.get("cow"), equalTo(1));
    }

    @Test
    public void testCount_caseSensitiveTrue() throws Exception {
        when(configuration.getWordsSkip()).thenReturn(emptyList());
        when(configuration.getCaseSensitive()).thenReturn(true);

        String original = "cow COW cOw";
        Map<String, Integer> result = wordCounter.count(original);

        assertThat(result.size(), equalTo(3));
        assertThat(result.get("cow"), equalTo(1));
        assertThat(result.get("COW"), equalTo(1));
        assertThat(result.get("cOw"), equalTo(1));
    }

    @Test
    public void testCount_caseSensitiveFalse() throws Exception {
        when(configuration.getWordsSkip()).thenReturn(emptyList());
        when(configuration.getCaseSensitive()).thenReturn(false);

        String original = "cow COW cOw";
        Map<String, Integer> result = wordCounter.count(original);

        assertThat(result.size(), equalTo(1));
        assertThat(result.get("cow"), equalTo(3));
    }

    @Test
    public void testCount_skipsWords() throws Exception {
        when(configuration.getWordsSkip()).thenReturn(asList("a", "cool"));
        when(configuration.getCaseSensitive()).thenReturn(false);

        String original = "a cool cow";
        Map<String, Integer> result = wordCounter.count(original);

        assertThat(result.size(), equalTo(1));
        assertThat(result.get("cow"), equalTo(1));
    }
}
