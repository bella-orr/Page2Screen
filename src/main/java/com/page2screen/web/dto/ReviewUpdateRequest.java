package com.page2screen.web.dto;

import jakarta.validation.constraints.*;

public class ReviewUpdateRequest {
  @NotNull
  @Min(1)
  @Max(10)
  private Integer rating;
  @NotBlank
  private String title;
  @NotBlank
  private String body;
  private Boolean containsSpoilers = false;

  public Integer getRating() { return rating; }
  public void setRating(Integer rating) { this.rating = rating; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getBody() { return body; }
  public void setBody(String body) { this.body = body; }
  public Boolean getContainsSpoilers() { return containsSpoilers; }
  public void setContainsSpoilers(Boolean containsSpoilers) { this.containsSpoilers = containsSpoilers; }
}
