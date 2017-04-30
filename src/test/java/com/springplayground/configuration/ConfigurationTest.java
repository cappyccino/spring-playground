package com.springplayground.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.contains;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
        "wordCount.caseSensitive=true",
        "wordCount.words.skip[0]=foo",
        "wordCount.words.skip[1]=bar"
})
public class ConfigurationTest {
    @Autowired private Configuration config;

    @Test
    public void testPropertiesAreMappedCorrectly() {
        assertTrue(config.getCaseSensitive());
        assertThat(config.getWordsSkip(), contains("foo", "bar"));
    }
}