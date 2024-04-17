package com.example.restservice.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LiveStreamTest {

    @Test
    void createNewMutableLiveStream() {
        MutableLiveStream stream = new MutableLiveStream();
        stream.setId(UUID.randomUUID().toString());
        //System.out.println(stream.getId());
        stream.setTitle("First Live Stream");
        stream.setDescription("Rookie's first attempt at streaming");
        stream.setUrl("https://youtube.com");
        stream.setStartDate(LocalDateTime.of(2023, 12, 01, 11, 30));
        stream.setEndDate(LocalDateTime.of(2023, 12, 01, 16, 30));

        assertNotNull(stream);
        assertEquals("First Live Stream", stream.getTitle(), "Title is incorrect");
    }

    @Test
    void createNewImmutableLiveStream() {
        ImmutableLiveStream stream = new ImmutableLiveStream(
                UUID.randomUUID().toString(),
                "First Live Stream",
                "Rookie's first attempt at streaming",
                "https://youtube.com",
                LocalDateTime.of(2023, 12, 01, 11, 30),
                LocalDateTime.of(2023, 12, 01, 16, 30)
        );
        assertNotNull(stream);
        assertEquals("First Live Stream", stream.getTitle(), "Title is incorrect");
    }

    @Test
    void createNewRecordLiveStream() {
        LiveStream stream = new LiveStream(
                UUID.randomUUID().toString(),
                "First Live Stream",
                "Rookie's first attempt at streaming",
                "https://youtube.com",
                LocalDateTime.of(2023, 12, 01, 11, 30),
                LocalDateTime.of(2023, 12, 01, 16, 30)
        );
        assertNotNull(stream);
        assertEquals("First Live Stream", stream.title());
        assertTrue(stream.getClass().isRecord());
        assertEquals(6, stream.getClass().getRecordComponents().length);
    }
}
