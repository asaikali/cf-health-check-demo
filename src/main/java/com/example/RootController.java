package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @Autowired
    private ExampleHealthIndicator exampleHealthIndicator;

    @GetMapping
    public String get()
    {
        return "Health Checks Will Pass: "  + exampleHealthIndicator.getState();
    }

    @GetMapping("/fail")
    public String fail()
    {
        exampleHealthIndicator.setState(false);
        return "Health Checks Will Pass: "  + exampleHealthIndicator.getState();
    }

    @GetMapping("/pass")
    public String pass()
    {
        exampleHealthIndicator.setState(true);
        return "Health Checks Will Pass: "  + exampleHealthIndicator.getState();
    }
}
