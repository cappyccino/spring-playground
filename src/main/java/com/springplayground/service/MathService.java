package com.springplayground.service;

import org.springframework.stereotype.Service;

@Service
public class MathService {

    public String calculate(String operation, Integer x, Integer y) {
        switch (operation) {
            case "add":
                return String.format("%d + %d = %d", x, y, x + y);
            case "subtract":
                return String.format("%d - %d = %d", x, y, x - y);
            case "multiply":
                return String.format("%d * %d = %d", x, y, x * y);
            case "divide":
                return String.format("%d / %d = %d", x, y, x / y);
            default:
                return "Not a valid operator! Please try again";
        }
    }

    public String getSum(Integer[] numbers) {
        StringBuilder resultBuilder = new StringBuilder();
        Integer sum = 0;

        for (int i = 0; i < numbers.length; i++) {
            if (i > 0) {
                resultBuilder.append(" + ");
            }
            resultBuilder.append(numbers[i]);
            sum += numbers[i];
        }

        return resultBuilder.toString() + " = " + sum;
    }

    public String getVolume(Integer length, Integer width, Integer height) {
        Integer volume = length * width * height;
        return String.format(
                "The volume of a %dx%dx%d rectangle is %d", length, width, height, volume);
    }
}
