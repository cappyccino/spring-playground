package com.springplayground.controller;

import com.springplayground.service.MathService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({MathController.class, MathService.class})
@AutoConfigureMockMvc(secure=false)
public class MathControllerTest {

    @Autowired
    MockMvc mvc;

    @Mock
    MathService mathService;

    @InjectMocks
    private MathController controller;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPi() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/math/pi");
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("3.141592653589793"));
    }

    @Test
    public void testGetCalculations_returns200() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/math/calculate?operation=multiply&x=3&y=5");

        this.mvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testGetCalculations_withOptionalParamMissingReturns200() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/math/calculate?x=30&y=5");

        this.mvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testGetCalculations_returns4xxWithMissingRequiredParams() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/math/calculate");

        this.mvc.perform(request).andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetCalculations_callsMathService() throws Exception {
        controller.getCalculations("divide", 20, 5);

        verify(mathService, times(1)).calculate("divide", 20, 5);
        verifyNoMoreInteractions(mathService);
    }

    @Test
    public void testPostSum_returns200() throws Exception {
        RequestBuilder request = post("/math/sum?n=4&n=5&n=6");
        this.mvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testPostSum_callsMathService() throws Exception {
        Integer[] params = {1, 2, 3};
        controller.postSum(params);

        verify(mathService, times(1)).getSum(params);
        verifyNoMoreInteractions(mathService);
    }

    @Test
    public void testVolume_returns200() throws Exception {
        RequestBuilder postRequest = post("/math/volume/3/4/5");
        RequestBuilder patchRequest = MockMvcRequestBuilders.patch("/math/volume/6/7/8");

        this.mvc.perform(postRequest).andExpect(status().isOk());
        this.mvc.perform(patchRequest).andExpect(status().isOk());
    }

    @Test
    public void testCalculateVolume_callsMathService() throws Exception {
        controller.calculateVolume(3, 4, 5);

        verify(mathService, times(1)).getVolume(3, 4, 5);
        verifyNoMoreInteractions(mathService);
    }

    @Test
    public void testPostArea_returns200ForCircle() throws Exception {
        MockHttpServletRequestBuilder request = post("/math/area")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("type", "circle")
                .param("radius", "4");

        this.mvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testPostArea_returns200ForRectangle() throws Exception {
        MockHttpServletRequestBuilder request = post("/math/area")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("type", "rectangle")
                .param("width", "4")
                .param("height", "7");

        this.mvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testPostArea_callsMathService() throws Exception {
        Map content = new HashMap<String, String>();
        content.put("type", "circle");
        content.put("radius", "4");
        controller.postArea(content);

        verify(mathService, times(1)).getArea(anyInt());
        verifyNoMoreInteractions(mathService);
    }

    @Test
    public void testPostArea_returnsInvalidForInvalidContent() throws Exception {
        MockHttpServletRequestBuilder request = post("/math/area")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("invalid", "parameters");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid"));
    }
}
