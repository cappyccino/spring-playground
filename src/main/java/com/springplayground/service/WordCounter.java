package com.springplayground.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WordCounter {

    public Map<String, Integer> count(String original) {
        Map<String, Integer> countMap = new HashMap<>();

        String[] split = original.replaceAll("[^A-Za-z0-9 ]", "").split(" ");

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
