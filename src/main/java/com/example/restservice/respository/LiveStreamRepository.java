package com.example.restservice.respository;

import com.example.restservice.exception.LiveStreamNotFoundException;
import com.example.restservice.model.LiveStream;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class LiveStreamRepository {

    List<LiveStream> streams = new ArrayList<>();

    public LiveStreamRepository() {
        streams.add(new LiveStream(
                UUID.randomUUID().toString(),
                "First Live Stream",
                "Rookie's first attempt at streaming",
                "https://youtube.com",
                LocalDateTime.of(2023, 12, 01, 11, 30),
                LocalDateTime.of(2023, 12, 01, 16, 30)
        ));
    }

    public List<LiveStream> findAll() {
        return streams;
    }

    public LiveStream findById(String id) throws LiveStreamNotFoundException {
        return streams.stream().filter(stream -> stream.id().equals(id)).findFirst().orElseThrow(LiveStreamNotFoundException::new);
    }

    public LiveStream create(LiveStream stream) {
        streams.add(stream);
        return stream;
    }

    public void update(LiveStream stream, String id) {
        LiveStream findExistingStream = streams.stream()
                .filter(s -> s.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Stream not found"));

        int index = streams.indexOf(findExistingStream);
        streams.set(index, stream);
    }

    public void delete(String id) {
        streams.removeIf(stream -> stream.id().equals(id));
    }

}

