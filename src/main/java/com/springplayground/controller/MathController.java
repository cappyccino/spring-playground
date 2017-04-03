package com.springplayground.controller;

import com.springplayground.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/math")
public class MathController {

    private MathService mathService;

    @Autowired
    public void MathController(MathService mathService) {
        this.mathService = mathService;
    }

    @GetMapping("/pi")
    public String getPi() {
        return Double.toString(Math.PI);
    }

    @GetMapping("/calculate")
    public String getCalculations(
            @RequestParam(value = "operation", defaultValue = "add") String operation,
            @RequestParam(value = "x") Integer x,
            @RequestParam(value = "y") Integer y
    ) {
        return mathService.calculate(operation, x, y);
    }

    @PostMapping("/sum")
    public String postSum(
            @RequestParam(value = "n") Integer[] numbersArray
    ) {
        return mathService.getSum(numbersArray);
    }

    @RequestMapping("/volume/{length}/{width}/{height}")
    public String calculateVolume(
            @PathVariable Integer length,
            @PathVariable Integer width,
            @PathVariable Integer height) {
        return mathService.getVolume(length, width, height);
    }
}
