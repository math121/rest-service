package com.example.restservice.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Record is good for immutable classes, where you define the parameters as below
 * The record will take care of getting parameter values without you having to define getters
 *
 * Look at target/classes/com/example/restservice/model/LiveStream -> the generated code will have constructor and getters
 */
public record LiveStream(
        @NotEmpty String id, // Bean validation, need to add @Valid to controller method that takes in this data
        @NotNull @NotEmpty String title,
        String description,
        String url,
        LocalDateTime startDate,
        LocalDateTime endDate
) {

    /*
    You can still make constructors here for example if I want to validate some parameters
     */
}
