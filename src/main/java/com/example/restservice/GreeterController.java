package com.example.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Telling spring boot that this is a class for request mappings
 */
@RestController
public class GreeterController {

    private static final String template = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();

    /**
     * Mapping to Greeter | /greeting?name=Dan -> when params are given
     * @param name takes in a parameter of String type
     * @return return Greeter object in JSON
     */
    @GetMapping("/greeting")
    public Greeter greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeter(counter.incrementAndGet(), String.format(template, name));
    }
}
