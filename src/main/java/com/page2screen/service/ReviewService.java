package com.page2screen.service;

import com.page2screen.domain.Review;
import com.page2screen.domain.Work;
import com.page2screen.domain.MediaType;
import com.page2screen.repo.ReviewRepository;
import com.page2screen.repo.WorkRepository;
import com.page2screen.web.dto.ReviewCreateRequest;
import com.page2screen.web.dto.ReviewUpdateRequest;
import com.page2screen.web.dto.ReviewResponse;
import com.page2screen.web.dto.WorkResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
  private final WorkRepository workRepo;
  private final ReviewRepository reviewRepo;

  public ReviewService(WorkRepository workRepo, ReviewRepository reviewRepo) {
    this.workRepo = workRepo;
    this.reviewRepo = reviewRepo;
  }

  @Transactional(readOnly = true)
  public Work getWork(UUID workId) {
    return workRepo.findById(workId).orElseThrow(() -> new IllegalArgumentException("work not found"));
  }

  @Transactional(readOnly = true)
  public WorkResponse getWorkDetail(UUID workId) {
    Work work = workRepo.findWithReviewsById(workId).orElseThrow(() -> new IllegalArgumentException("work not found"));
    return toWorkResponse(work);
  }

  @Transactional
  public WorkResponse createWork(String title, MediaType mediaType, Integer releaseYear) {
    Work w = new Work();
    w.setId(UUID.randomUUID());
    w.setTitle(title);
    w.setMediaType(mediaType);
    w.setReleaseYear(releaseYear);
    w.setCreatedAt(OffsetDateTime.now());
    w.setUpdatedAt(w.getCreatedAt());
    Work saved = workRepo.save(w);
    return toWorkResponse(saved);
  }

  @Transactional(readOnly = true)
  public List<ReviewResponse> listReviews(UUID workId) {
    return reviewRepo.findByWorkId(workId).stream()
        .map(review -> toReviewResponse(review, workId))
        .toList();
  }

  @Transactional
  public ReviewResponse createReview(UUID workId, ReviewCreateRequest req) {
    Work work = getWork(workId);
    reviewRepo.findByWorkIdAndAuthorId(workId, req.getAuthorId()).ifPresent(r -> { throw new IllegalStateException("review already exists for this user and work"); });
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
    return toReviewResponse(saved, workId);
  }

  @Transactional
  public ReviewResponse updateReview(UUID reviewId, ReviewUpdateRequest req) {
    Review r = reviewRepo.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("review not found"));
    r.setRating(req.getRating());
    r.setTitle(req.getTitle());
    r.setBody(req.getBody());
    r.setContainsSpoilers(Boolean.TRUE.equals(req.getContainsSpoilers()));
    r.setUpdatedAt(OffsetDateTime.now());
    Review saved = reviewRepo.save(r);
    UUID workId = saved.getWork() != null ? saved.getWork().getId() : null;
    return toReviewResponse(saved, workId);
  }

  @Transactional
  public void deleteReview(UUID reviewId) {
    if (!reviewRepo.existsById(reviewId)) throw new IllegalArgumentException("review not found");
    reviewRepo.deleteById(reviewId);
  }

  private WorkResponse toWorkResponse(Work work) {
    List<ReviewResponse> reviews = work.getReviews().stream()
        .map(review -> toReviewResponse(review, work.getId()))
        .toList();
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

  private ReviewResponse toReviewResponse(Review review, UUID workId) {
    UUID resolvedWorkId = workId != null
        ? workId
        : (review.getWork() != null ? review.getWork().getId() : null);
    int likes = review.getLikes() != null ? review.getLikes() : 0;
    boolean containsSpoilers = Boolean.TRUE.equals(review.getContainsSpoilers());
    ReviewResponse.AuthorSummary author = new ReviewResponse.AuthorSummary(
        review.getAuthorId(),
        review.getAuthorDisplayName()
    );
    return new ReviewResponse(
        review.getId(),
        resolvedWorkId,
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
