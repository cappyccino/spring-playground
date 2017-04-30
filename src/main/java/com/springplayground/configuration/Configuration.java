package com.springplayground.configuration;

import com.springplayground.service.WordCounter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@org.springframework.context.annotation.Configuration
@ConfigurationProperties("wordCount")
public class Configuration {

    @Bean
    public WordCounter getWordCounter(Configuration config) {
        return new WordCounter(config);
    }

    private Boolean caseSensitive;
    private Words words;

    public Boolean getCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(Boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public List<String> getWordsSkip() {
        return words.getSkip();
    }

    public Words getWords() { return words; }

    public void setWords(Words words) { this.words = words; }

    public static class Words {
        private List<String> skip;

        public List<String> getSkip() {
            return skip;
        }

        public void setSkip(List<String> skip) {
            this.skip = skip;
        }
    }
}
