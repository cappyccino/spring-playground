package com.springplayground.service;

import com.springplayground.configuration.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCounter {
    private final Configuration config;

    public WordCounter(Configuration config) {
        this.config = config;
    }

    public Map<String, Integer> count(String original) {
        Map<String, Integer> countMap = new HashMap<>();
        List<String> skipped = config.getWordsSkip();

        String[] split = config.getCaseSensitive()
                ? original.replaceAll("[^A-Za-z0-9 ]", "").split(" ")
                : original.replaceAll("[^A-Za-z0-9 ]", "").toLowerCase().split(" ");

        Arrays.asList(split).stream()
                .filter(word -> !skipped.contains(word))
                .forEach((String word) -> {
                    Integer count = countMap.get(word);
                    if (count == null) {
                        countMap.put(word, 1);
                    } else {
                        countMap.put(word, count + 1);
                    }
                });

        return countMap;
    }
}
