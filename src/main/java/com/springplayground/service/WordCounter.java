package com.springplayground.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class WordCounter {

    @Bean
    public String getString() {
        return new String();
    }

    @Bean
    public Map<String, Integer> count(String original) {
        Map<String, Integer> countMap = new HashMap<>();

        String[] split = original.split(" ");

        Arrays.asList(split).forEach((String word) -> {
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
