package com.page2screen.domain;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Table(name = "works")
public class Work {
  @Id
  @Column(nullable = false, updatable = false)
  private UUID id;

  @Column(nullable = false)
  private String title;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MediaType mediaType;

  @Column(nullable = false)
  private Integer releaseYear;

  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;

  @OneToMany(mappedBy = "work", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews = new ArrayList<>();

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public MediaType getMediaType() { return mediaType; }
  public void setMediaType(MediaType mediaType) { this.mediaType = mediaType; }
  public Integer getReleaseYear() { return releaseYear; }
  public void setReleaseYear(Integer ReleaseYear) { this.ReleaseYear = ReleaseYear; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
  public OffsetDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
  public List<Review> getReviews() { return reviews; }
}
