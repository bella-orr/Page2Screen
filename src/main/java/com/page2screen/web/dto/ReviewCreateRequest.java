package com.page2screen.web.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public class ReviewCreateRequest {
/**
 * Request DTO for creating a new review.
 * Validates all required fields and enforces size constraints.
 */
public class ReviewCreateRequest {
  @NotNull(message = "Author ID is required")
  private UUID authorId;
    
  @NotBlank(message = "Author display name is required")
  @Size(max = 100, message = "Author display name must not exceed 100 characters")
  private String authorDisplayName;
    
  @NotNull(message = "Rating is required")
  @Min(value = 1, message = "Rating must be between 1 and 10")
  @Max(value = 10, message = "Rating must be between 1 and 10")
  private Integer rating;
    
  @NotBlank(message = "Title is required")
  @Size(max = 255, message = "Title must not exceed 255 characters")
  private String title;
    
  @NotBlank(message = "Review body is required")
  @Size(max = 5000, message = "Review body must not exceed 5000 characters")
  private String body;
    
  private Boolean containsSpoilers = false;
  private Boolean containsSpoilers = false;

  public UUID getAuthorId() { return authorId; }
  public void setAuthorId(UUID authorId) { this.authorId = authorId; }
  public String getAuthorDisplayName() { return authorDisplayName; }
  public void setAuthorDisplayName(String authorDisplayName) { this.authorDisplayName = authorDisplayName; }
  public Integer getRating() { return rating; }
  public void setRating(Integer rating) { this.rating = rating; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getBody() { return body; }
  public void setBody(String body) { this.body = body; }
  public Boolean getContainsSpoilers() { return containsSpoilers; }
  public void setContainsSpoilers(Boolean containsSpoilers) { this.containsSpoilers = containsSpoilers; }
}
