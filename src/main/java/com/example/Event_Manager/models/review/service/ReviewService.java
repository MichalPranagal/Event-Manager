package com.example.Event_Manager.models.review.service;

import com.example.Event_Manager.models.review.dto.request.CreateReviewDTO;
import com.example.Event_Manager.models.review.dto.request.UpdateReviewDTO;
import com.example.Event_Manager.models.review.dto.response.ReviewDTO;
import com.example.Event_Manager.models.review.dto.response.ReviewSummaryDTO;
import com.example.Event_Manager.models.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO:: zrobic implementacje tych metod

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public ReviewDTO createReview(CreateReviewDTO review, Long userId) {
        return null;
    }

    @Override
    public ReviewDTO updateReview(Long reviewId, UpdateReviewDTO review, Long userId) {
        return null;
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) {

    }

    @Override
    public List<ReviewDTO> getReviewsForEvent(Long eventId) {
        return List.of();
    }

    @Override
    public ReviewSummaryDTO getEventReviewSummary(Long eventId) {
        return null;
    }
}
