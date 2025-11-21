package com.example.Event_Manager.models.review.validation;

import com.example.Event_Manager.models.review.Review;
import com.example.Event_Manager.models.review.exceptions.ReviewNotFoundException;
import com.example.Event_Manager.models.util.BaseValidation;
import com.example.Event_Manager.models.util.RequestEmptyException;
import org.springframework.stereotype.Component;

@Component
public class ReviewValidation implements BaseValidation {
    public void checkIfReviewExists(Review review) {
        if (review == null) {
            throw new ReviewNotFoundException("Review does not exist.");
        }
    }

    @Override
    public void checkIfRequestNotNull(Object request) {
        if (request == null) {
            throw new RequestEmptyException("Request cannot be null.");
        }
    }
}
