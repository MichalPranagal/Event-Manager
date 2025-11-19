package com.example.Event_Manager.models.review.dto.response;

import com.example.Event_Manager.models.event.Event;

import java.util.Map;

public record ReviewSummaryDTO(
        Long eventId,
        String eventName,
        Double averageRating,
        Integer totalReviews,
        Map<String, Event> eventRatings
) {
}
