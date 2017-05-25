package com.springplayground.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
public class MovieServiceTest {
    @Test
    public void testGetMovie() throws Exception {
        MovieService service = new MovieService();

        MockRestServiceServer mockServer =
                MockRestServiceServer.createServer(service.getRestTemplate());

        mockServer
                .expect(requestTo("http://www.omdbapi.com/?s=somemovie"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("The Best Movie!", MediaType.APPLICATION_JSON));

        assertThat(service.getOmdbMovie("somemovie"), equalTo("The Best Movie!"));
        mockServer.verify();
    }
}
