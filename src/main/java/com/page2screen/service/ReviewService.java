package com.page2screen.service;

import com.page2screen.domain.Review;
import com.page2screen.domain.Work;
import com.page2screen.domain.MediaType;
import com.page2screen.repo.ReviewRepository;
import com.page2screen.repo.WorkRepository;
import com.page2screen.service.ResponseMapper;
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
  private final ResponseMapper mapper;

  private static final String WORK_NOT_FOUND        = "Work not found";
  private static final String REVIEW_NOT_FOUND      = "Review not found";
  private static final String REVIEW_ALREADY_EXISTS = "Review already exists for this user and work";
  private static final String TITLE_NULL_OR_EMPTY   = "Title cannot be NULL or empty";
  private static final String REQUEST_CANNOT_BE_NULL = "Request cannot be NULL";

  public ReviewService(WorkRepository workRepo, ReviewRepository reviewRepo, ResponseMapper mapper) {
    this.workRepo = workRepo;
    this.reviewRepo = reviewRepo;
    this.mapper = mapper;
  }

  @Transactional(readOnly = true)
  public Work getWork(UUID workId) {
    return workRepo.findById(workId).orElseThrow(() -> new IllegalArgumentException(WORK_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public WorkResponse getWorkDetail(UUID workId) {
    Work work = workRepo.findWithReviewsById(workId).orElseThrow(() -> new IllegalArgumentException(WORK_NOT_FOUND));
    return mapper.toWorkResponse(work);
  }

  @Transactional
  public WorkResponse createWork(String title, MediaType mediaType, Integer releaseYear) {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException(TITLE_NULL_OR_EMPTY);
    }

    Work w = new Work();
    w.setId(UUID.randomUUID());
    w.setTitle(title);
    w.setMediaType(mediaType);
    w.setReleaseYear(releaseYear);
    w.setCreatedAt(OffsetDateTime.now());
    w.setUpdatedAt(w.getCreatedAt());
    Work saved = workRepo.save(w);
    return mapper.toWorkResponse(saved);
  }

  @Transactional(readOnly = true)
  public List<ReviewResponse> listReviews(UUID workId) {
    return reviewRepo.findByWorkId(workId).stream()
        .map(mapper::toReviewResponse)
        .toList();
  }

  @Transactional
  public ReviewResponse createReview(UUID workId, ReviewCreateRequest req) {
    Work work = getWork(workId);
    reviewRepo.findByWorkIdAndAuthorId(workId, req.getAuthorId()).ifPresent(r -> {
      throw new IllegalStateException(REVIEW_ALREADY_EXISTS);
    });
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
    return mapper.toReviewResponse(saved);
  }

  @Transactional
  public ReviewResponse updateReview(UUID reviewId, ReviewUpdateRequest req) {
    if (req == null) {
      throw new IllegalArgumentException(REQUEST_CANNOT_BE_NULL);
    }

    Review r = reviewRepo.findById(reviewId).orElseThrow(() -> new IllegalArgumentException(REVIEW_NOT_FOUND));
    r.setRating(req.getRating());
    r.setTitle(req.getTitle());
    r.setBody(req.getBody());
    r.setContainsSpoilers(Boolean.TRUE.equals(req.getContainsSpoilers()));
    r.setUpdatedAt(OffsetDateTime.now());
    Review saved = reviewRepo.save(r);
    return mapper.toReviewResponse(saved);
  }

  @Transactional
  public void deleteReview(UUID reviewId) {
    if (!reviewRepo.existsById(reviewId)) throw new IllegalArgumentException(REVIEW_NOT_FOUND);
    reviewRepo.deleteById(reviewId);
  }
}
