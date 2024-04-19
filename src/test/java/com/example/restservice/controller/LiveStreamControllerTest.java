package com.example.restservice.controller;

import com.example.restservice.model.LiveStream;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class) // sets the order in which the tests should run
public class LiveStreamControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    private ResponseEntity<List<LiveStream>> findAllStreams() {
        return testRestTemplate.exchange("/streams",
                HttpMethod.GET,
                new HttpEntity<>(null),
                new ParameterizedTypeReference<List<LiveStream>>() {});
    }

    @Test
    @Order(1)
    void findAll() {
        ResponseEntity<List<LiveStream>> entity = findAllStreams();
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, entity.getHeaders().getContentType());
        //System.out.println(entity.getBody());
        assertEquals(1, entity.getBody().size()); // gets the number of items that's in the JSON body
    }

    @Test
    @Order(2)
    void findById() {
        LiveStream existingLiveStream = findAllStreams().getBody().get(0);
        String id = existingLiveStream.id();
        String description = "Rookie's first attempt at streaming";

        LiveStream stream = testRestTemplate.getForObject("/streams/" + id, LiveStream.class);
        assertEquals(id, stream.id());
        assertEquals("First Live Stream", stream.title());
        assertEquals(description, stream.description());
    }

    @Test
    @Order(3)
    void create() {
        String id = UUID.randomUUID().toString();
        LiveStream stream = new LiveStream(
                id,
                "TEST Live Stream",
                "Live Stream creating for testing purposes",
                "https://youtube.com",
                LocalDateTime.of(2024, 12, 03, 15, 30),
                LocalDateTime.of(2024, 12, 03, 20, 0)
        );

        ResponseEntity<LiveStream> entity = testRestTemplate.postForEntity("/streams", stream, LiveStream.class);
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        assertEquals(2, findAllStreams().getBody().size());

        LiveStream newStream = entity.getBody();
        assertEquals(id, newStream.id());
        assertEquals("TEST Live Stream", newStream.title());
        assertEquals("Live Stream creating for testing purposes", newStream.description());
        assertEquals("https://youtube.com", newStream.url());
        assertEquals(LocalDateTime.of(2024, 12, 03, 15, 30), newStream.startDate());
        assertEquals(LocalDateTime.of(2024, 12, 03, 20, 0), newStream.endDate());

    }

    @Test
    @Order(4)
    void update() {
        LiveStream existing = findAllStreams().getBody().get(0);
        LiveStream updateStream = new LiveStream(
                existing.id(),
                "Updating this stream .....",
                existing.description(),
                existing.url(),
                existing.startDate(),
                existing.endDate()
        );

        ResponseEntity<LiveStream> entity = testRestTemplate.exchange(
                "/streams/" + existing.id(),
                HttpMethod.PUT,
                new HttpEntity<>(updateStream),
                LiveStream.class
        );
        assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
    }

    @Test
    @Order(5)
    void delete() {
        LiveStream existing = findAllStreams().getBody().get(0);
        ResponseEntity<LiveStream> entity = testRestTemplate.exchange(
                "/streams/" + existing.id(),
                HttpMethod.DELETE,
                null,
                LiveStream.class
        );
        assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
        assertEquals(1, findAllStreams().getBody().size());
    }
}
