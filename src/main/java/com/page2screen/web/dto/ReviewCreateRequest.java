package com.page2screen.web.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public class ReviewCreateRequest {
  @NotNull
  private UUID authorId;
  @NotBlank
  private String authorDisplayName;
  @NotNull
  @Min(1)
  @Max(10)
  private Integer rating;
  @NotBlank
  private String title;
  @NotBlank
  private String body;
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
