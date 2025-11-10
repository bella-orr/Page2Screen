package com.page2screen.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Entity representing a user review of a work (book or movie).
 * Enforces business rule: one review per author per work via unique constraint.
 */
@Entity
@Table(name = "reviews", uniqueConstraints = {
  @UniqueConstraint(name = "uq_work_author", columnNames = {"work_id", "author_id"})
})
public class Review {
  @Id
  @Column(nullable = false, updatable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "work_id", nullable = false)
  private Work work;

  @Column(name = "author_id", nullable = false)
  private UUID authorId;

  @NotNull
  @Size(max = 100)
  @Column(nullable = false, length = 100)  // ✓ Explicit database length
  private String authorDisplayName;

  @NotNull
  @Min(1)
  @Max(10)
  @Column(nullable = false)
  private Integer rating;

  @NotNull
  @Size(max = 255)
  @Column(nullable = false, length = 255)  // ✓ Explicit database length
  private String title;

    @NotNull
    @Size(max = 5000)
    @Lob
    @Column(nullable = false, length = 5000)  // ✓ Practical limit even for LOB
    private String body;

    @Column(nullable = false)
    private Boolean containsSpoilers = false;

    @Column(nullable = false)
    private Integer likes = 0;

    @Column(nullable = false)
    private OffsetDateTime createdAt;
    
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }
  public Work getWork() { return work; }
  public void setWork(Work work) { this.work = work; }
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
  public Integer getLikes() { return likes; }
  public void setLikes(Integer likes) { this.likes = likes; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
  public OffsetDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
