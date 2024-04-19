package com.example.restservice.controller;

import com.example.restservice.exception.LiveStreamNotFoundException;
import com.example.restservice.model.LiveStream;
import com.example.restservice.respository.LiveStreamRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
Postman testing
Get: localhost:8080/streams
Get: localhost:8080/a41c983d-d9fb-4fcc-8ccc-323d11d20e9c  - this is test id, need to copy and paste the generated one
Post: localhost:8080/streams
Put: localhost:8080/streams/a41c983d-d9fb-4fcc-8ccc-323d11d20e9c
Delete: localhost:8080/streams/04f0ccd0-78be-4b28-99f4-fdbfeb6c0548
 */

/**
 * Controllers are for business logic, while data is handled by repositories
 */
@RestController
@RequestMapping("/streams") //mapping route for all the methods below
public class LiveStreamController {

    private final LiveStreamRepository liveStreamRepository;

    @Autowired
    public LiveStreamController(LiveStreamRepository repository) { // this takes in parameter, so can easily unit test without thinking about external databases
        liveStreamRepository = repository;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<LiveStream> findAll() {
        return liveStreamRepository.findAll();
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{id}")
    public LiveStream findById(@PathVariable String id) throws LiveStreamNotFoundException { //PathVariable pulls out the id from the path -> (/streams/{id})
        return liveStreamRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public LiveStream create(@Valid @RequestBody LiveStream stream) { //@RequestBody - to get the JSON data sent in
        return liveStreamRepository.create(stream);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT) // good practice for nothing being returned
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody LiveStream stream, @PathVariable String id) {
        liveStreamRepository.update(stream, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        liveStreamRepository.delete(id);
    }

    //public ResponseEntity<LiveStream>  ->
    // use this if you want to manipulate the response headers for example for authentication
}
