package com.page2screen.service;

import com.page2screen.domain.Review;
import com.page2screen.web.dto.ReviewResponse;

import java.util.UUID;

/**
 * Utility class for converting Review entities to DTOs.
 */
public class ReviewMapper {
    
    /**
     * Convert a Review entity to ReviewResponse DTO.
     * @param review the review entity
     * @return the review response DTO
     */
    public static ReviewResponse toReviewResponse(Review review) {
        UUID workId = review.getWork() != null ? review.getWork().getId() : null;
        int likes = review.getLikes() != null ? review.getLikes() : 0;
        boolean containsSpoilers = Boolean.TRUE.equals(review.getContainsSpoilers());
        ReviewResponse.AuthorSummary author = new ReviewResponse.AuthorSummary(
            review.getAuthorId(),
            review.getAuthorDisplayName()
        );

        return new ReviewResponse(
            review.getId(),
            workId,
            author,
            review.getRating(),
            review.getTitle(),
            review.getBody(),
            containsSpoilers,
            likes,
            review.getCreatedAt(),
            review.getUpdatedAt()
        );
    }
}
