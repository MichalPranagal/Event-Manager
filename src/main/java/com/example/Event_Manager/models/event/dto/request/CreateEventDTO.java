package com.example.Event_Manager.models.event.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateEventDTO(

        @NotNull(message = "Event name is required")
        String name,

        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description,

        @NotNull(message = "Event date is required")
        @Future
        LocalDateTime date,

        @NotNull(message = "Venue id is required")
        Long venueId,

        @NotNull(message = "Category id is required")
        Long categoryId
) {
}
