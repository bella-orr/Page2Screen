package com.page2screen.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entity representing a work of media (book or movie) that can be reviewed.
 */
@Entity
@Table(name = "works")
public class Work {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false, length = 255)  // explicit database length
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)  // enough for enum values
    private MediaType mediaType;

    @NotNull
    @Min(1800)
    @Column(nullable = false)
    private Integer releaseYear;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
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

