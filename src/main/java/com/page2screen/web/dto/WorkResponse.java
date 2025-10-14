package com.page2screen.web.dto;

import com.page2screen.domain.MediaType;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record WorkResponse(
    UUID id,
    String title,
    MediaType mediaType,
    Integer releaseYear,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt,
    List<ReviewResponse> reviews
) {}
