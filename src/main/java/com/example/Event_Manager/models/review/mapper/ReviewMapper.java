package com.example.Event_Manager.models.review.mapper;

import com.example.Event_Manager.models.event.Event;
import com.example.Event_Manager.models.review.Review;
import com.example.Event_Manager.models.review.dto.request.CreateReviewDTO;
import com.example.Event_Manager.models.review.dto.request.UpdateReviewDTO;
import com.example.Event_Manager.models.review.dto.response.ReviewDTO;
import com.example.Event_Manager.models.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReviewMapper {
    public ReviewDTO toDTO(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getEvent().getId(),
                review.getEvent().getName(),
                review.getUser().getId(),
                review.getUser().getFullName(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }

    public Review toEntity(CreateReviewDTO dto, Event event, User user) {
        Review review = new Review();
        review.setEvent(event);
        review.setUser(user);
        review.setRating(dto.rating());
        review.setComment(dto.comment());
        review.setCreatedAt(LocalDateTime.now());
        return review;
    }

    public void updateEntity(Review review, UpdateReviewDTO dto) {
        review.setRating(dto.rating());
        review.setComment(dto.comment());
    }
}
