package com.page2screen.web.dto;

import com.page2screen.domain.MediaType;
import jakarta.validation.constraints.*;

public class WorkCreateRequest {
  @NotBlank(message = "Title is required")
  @Size(max = 255, message = "Title must not exceed 255 characters")
  private String title;
    
  @NotNull(message = "Media type is required")
  private MediaType mediaType;
  
  @NotNull(message = "Release year is required")
  @Min(value = 1800, message = "Release year must be 1800 or later")
  @Max(value = 2100, message = "Release year must be 2100 or earlier")
  private Integer releaseYear;

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public MediaType getMediaType() { return mediaType; }
  public void setMediaType(MediaType mediaType) { this.mediaType = mediaType; }
  public Integer getReleaseYear() { return releaseYear; }
  public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }
}
