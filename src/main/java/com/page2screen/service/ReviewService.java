package com.page2screen.service;

import com.page2screen.domain.Review;
import com.page2screen.domain.Work;
import com.page2screen.repo.ReviewRepository;
import com.page2screen.web.dto.ReviewCreateRequest;
import com.page2screen.web.dto.ReviewResponse;
import com.page2screen.web.dto.ReviewUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service for managing Review entities.
 * Handles review CRUD operations and enforces business rules.
 */
@Service
public class ReviewService {
    private static final String REVIEW_NOT_FOUND = "Review not found";
    private static final String REVIEW_ALREADY_EXISTS = "Review already exists for this user and work";
    private static final String REQUEST_CANNOT_BE_NULL = "Request cannot be NULL";

    private final WorkService workService;
    private final ReviewRepository reviewRepo;

    public ReviewService(WorkService workService, ReviewRepository reviewRepo) {
        this.workService = workService;
        this.reviewRepo = reviewRepo;
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> listReviews(UUID workId) {
        return reviewRepo.findByWorkId(workId).stream()
            .map(ReviewMapper::toReviewResponse)
            .toList();
    }

    @Transactional
    public ReviewResponse createReview(UUID workId, ReviewCreateRequest req) {
        Work work = workService.getWork(workId);
        reviewRepo.findByWorkIdAndAuthorId(workId, req.getAuthorId())
            .ifPresent(r -> { throw new IllegalStateException(REVIEW_ALREADY_EXISTS); });

        Review r = new Review();
        r.setId(UUID.randomUUID());
        r.setWork(work);
        r.setAuthorId(req.getAuthorId());
        r.setAuthorDisplayName(req.getAuthorDisplayName());
        r.setRating(req.getRating());
        r.setTitle(req.getTitle());
        r.setBody(req.getBody());
        r.setContainsSpoilers(Boolean.TRUE.equals(req.getContainsSpoilers()));
        r.setLikes(0);
        r.setCreatedAt(OffsetDateTime.now());
        r.setUpdatedAt(r.getCreatedAt());

        Review saved = reviewRepo.save(r);
        return ReviewMapper.toReviewResponse(saved);
    }

    @Transactional
    public ReviewResponse updateReview(UUID reviewId, ReviewUpdateRequest req) {
        if (req == null) {
            throw new IllegalArgumentException(REQUEST_CANNOT_BE_NULL);
        }

        Review r = reviewRepo.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException(REVIEW_NOT_FOUND));
        r.setRating(req.getRating());
        r.setTitle(req.getTitle());
        r.setBody(req.getBody());
        r.setContainsSpoilers(Boolean.TRUE.equals(req.getContainsSpoilers()));
        r.setUpdatedAt(OffsetDateTime.now());

        Review saved = reviewRepo.save(r);
        return ReviewMapper.toReviewResponse(saved);
    }

    @Transactional
    public void deleteReview(UUID reviewId) {
        if (!reviewRepo.existsById(reviewId)) {
            throw new IllegalArgumentException(REVIEW_NOT_FOUND);
        }
        reviewRepo.deleteById(reviewId);
    }
}
