package com.example.Event_Manager.models.review.dto.response;

import java.time.LocalDateTime;

public record ReviewDTO(
        Long id,
        Long eventId,
        String eventName,
        Long userId,
        String userName,
        Integer rating,
        String comment,
        LocalDateTime createdAt
) {
}
