package com.page2screen.web.dto;

import com.page2screen.domain.MediaType;
import jakarta.validation.constraints.*;

public class WorkCreateRequest {
  @NotBlank
  private String title;
  @NotNull
  private MediaType mediaType;
  @NotNull
  @Min(1800)
  private Integer releaseYear;

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public MediaType getMediaType() { return mediaType; }
  public void setMediaType(MediaType mediaType) { this.mediaType = mediaType; }
  public Integer getReleaseYear() { return releaseYear; }
  public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }
}
