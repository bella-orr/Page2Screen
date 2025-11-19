package com.page2screen.web;

import com.page2screen.service.ReviewService;
import com.page2screen.web.dto.ReviewCreateRequest;
import com.page2screen.web.dto.ReviewResponse;
import com.page2screen.web.dto.ReviewUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ReviewController {
  private final ReviewService service;

  public ReviewController(ReviewService service) {
    this.service = service;
  }

  @GetMapping("/works/{workId}/reviews")
  public List<ReviewResponse> list(@PathVariable UUID workId) {
    return service.listReviews(workId);
  }

  @PostMapping("/works/{workId}/reviews")
  public ResponseEntity<ReviewResponse> create(@PathVariable UUID workId, @Valid @RequestBody ReviewCreateRequest req) {
    ReviewResponse review = service.createReview(workId, req);
    return ResponseEntity.created(URI.create("/api/reviews/" + review.id())).body(review);
  }

  @PutMapping("/reviews/{reviewId}")
  public ReviewResponse update(@PathVariable UUID reviewId, @Valid @RequestBody ReviewUpdateRequest req) {
    return service.updateReview(ReviewId, req);
  }

  @DeleteMapping("/reviews/{reviewId}")
  public ResponseEntity<Void> delete(@PathVariable UUID reviewId) {
    service.deleteReview(ReviewId);
    return ResponseEntity.noContent().build();
  }
}

