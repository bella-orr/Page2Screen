package com.page2screen.domain;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

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

  @Column(nullable = false)
  private String authorDisplayName;

  @Column(nullable = false)
  private Integer rating;

  @Column(nullable = false)
  private String title;

  @Lob
  @Column(nullable = false)
  private String body;

  @Column(nullable = false)
  private Boolean containsSpoilers = false;

  @Column(nullable = false)
  private Integer likes = 0;

  // Genereated persistent id if none exist and captures time of creation.
  @PrePersist
  private void prePersist() {
    if (id == null) {
      id = UUID.randomUUID();
    }
    OffsetDataTime now = OffsetDataTime.now();
    if (createdAt == null) {
      createdAt = now;
    }
    updatedAt = now;
  }
  
  private OffsetDateTime updatedAt;

  // Getters and Setters
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
