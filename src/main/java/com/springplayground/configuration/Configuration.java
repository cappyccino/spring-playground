package com.springplayground.configuration;

import com.springplayground.service.WordCounter;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public WordCounter getWordCounter() {
        return new WordCounter();
    }
}
