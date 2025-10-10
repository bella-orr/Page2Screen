package com.page2screen.web.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ReviewResponse(
    UUID id,
    UUID workId,
    AuthorSummary author,
    Integer rating,
    String title,
    String body,
    Boolean containsSpoilers,
    Integer likes,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
  public record AuthorSummary(UUID id, String displayName) {}
}
