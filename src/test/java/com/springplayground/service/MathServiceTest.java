package com.springplayground.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Service;

import static org.junit.Assert.assertEquals;

@Service
public class MathServiceTest {
    private MathService mathService;

    @Before
    public void setUp() {
        mathService = new MathService();
    }

    @Test
    public void testGetSum() throws Exception {
        Integer[] numbers = {4, 5, 6};

        String expectedResult = "4 + 5 + 6 = 15";
        assertEquals(mathService.getSum(numbers), expectedResult);
    }

    @Test
    public void testCalculateResult_withAddOperator() throws Exception {
         String operator = "add";
         Integer x = 4;
         Integer y = 6;

        String result = mathService.calculate(operator, x, y);
        assertEquals(result, "4 + 6 = 10");
    }

    @Test
    public void testCalculateResult_withMultiplyOperator() throws Exception {
         String operator = "multiply";
         Integer x = 4;
         Integer y = 6;

         String result = mathService.calculate(operator, x, y);
         assertEquals(result, "4 * 6 = 24");
    }

    @Test
    public void testCalculateResult_withSubtractOperator() throws Exception {
         String operator = "subtract";
         Integer x = 4;
         Integer y = 6;

         String result = mathService.calculate(operator, x, y);
         assertEquals(result, "4 - 6 = -2");
    }

    @Test
    public void testCalculateResult_withDivideOperator() throws Exception {
         String operator = "divide";
         Integer x = 30;
         Integer y = 5;
         String result = mathService.calculate(operator, x, y);
         assertEquals(result, "30 / 5 = 6");
    }

    @Test
    public void testGetVolume() throws Exception {
        String actualResult = mathService.getVolume(3, 4, 5);
        String expectedResult = "The volume of a 3x4x5 rectangle is 60";

        assertEquals(actualResult, expectedResult);
    }
}
