package com.page2screen.service;

import com.page2screen.domain.Review;
import com.page2screen.domain.Work;
import com.page2screen.web.dto.ReviewResponse;
import com.page2screen.web.dto.WorkResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Maps domain entities to DTO responses.
 */
@Component
public class ResponseMapper {

  public WorkResponse toWorkResponse(Work work) {
    List<ReviewResponse> reviews = work.getReviews() != null
        ? work.getReviews().stream().map(this::toReviewResponse).toList()
        : List.of();

    return new WorkResponse(
        work.getId(),
        work.getTitle(),
        work.getMediaType(),
        work.getReleaseYear(),
        work.getCreatedAt(),
        work.getUpdatedAt(),
        reviews
    );
  }

  public ReviewResponse toReviewResponse(Review review) {
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
