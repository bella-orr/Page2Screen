package com.page2screen.service;

import com.page2screen.domain.MediaType;
import com.page2screen.repo.WorkRepository;
import com.page2screen.web.dto.ReviewResponse;
import com.page2screen.web.dto.WorkResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MovieService {
  private static final UUID SAMPLE_MOVIE_ID   = UUID.fromString("33333333-3333-3333-3333-333333333333");
  private static final UUID SECOND_SAMPLE_ID  = UUID.fromString("44444444-4444-4444-4444-444444444444");
  private static final UUID SAMPLE_REVIEW_ID  = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");
  private static final UUID SAMPLE_AUTHOR_ID  = UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd");

  private final WorkRepository workRepo;

  public MovieService(WorkRepository workRepo) {
    this.workRepo = workRepo;
  }

  @Transactional(readOnly = true)
  public WorkResponse getMovie(UUID workId) {
    UUID resolvedId = workId != null ? workId : SAMPLE_MOVIE_ID;
    return buildSampleMovie(resolvedId);
  }

  @Transactional(readOnly = true)
  public List<WorkResponse> listMovies() {
    return List.of(
        buildSampleMovie(SAMPLE_MOVIE_ID),
        new WorkResponse(
            SECOND_SAMPLE_ID,
            "Moonlight Harbor",
            MediaType.MOVIE,
            2019,
            OffsetDateTime.now().minusYears(5),
            OffsetDateTime.now().minusYears(4).plusMonths(6),
            List.of()
        )
    );
  }

  @Transactional
  public WorkResponse createMovie(String title, Integer releaseYear) {
    OffsetDateTime now = OffsetDateTime.now();
    return new WorkResponse(
        UUID.randomUUID(),
        title != null && !title.isBlank() ? title : "Untitled Movie",
        MediaType.MOVIE,
        releaseYear != null ? releaseYear : now.getYear(),
        now,
        now,
        List.of()
    );
  }

  @Transactional
  public WorkResponse updateMovie(UUID workId, String title, Integer releaseYear) {
    OffsetDateTime now = OffsetDateTime.now();
    UUID resolvedId = workId != null ? workId : SAMPLE_MOVIE_ID;
    return new WorkResponse(
        resolvedId,
        title != null && !title.isBlank() ? title : "Revised Movie Title",
        MediaType.MOVIE,
        releaseYear != null ? releaseYear : now.getYear(),
        now.minusYears(1),
        now,
        List.of()
    );
  }

  @Transactional
  public void deleteMovie(UUID workId) {
    // Stub: no persistence yet. Once wired, remove sample-only implementation.
  }

  private WorkResponse buildSampleMovie(UUID workId) {
    OffsetDateTime created = OffsetDateTime.now().minusYears(3);
    OffsetDateTime updated = created.plusMonths(5);
    List<ReviewResponse> reviews = List.of(
        new ReviewResponse(
            SAMPLE_REVIEW_ID,
            workId,
            new ReviewResponse.AuthorSummary(SAMPLE_AUTHOR_ID, "Movie Buff"),
            8,
            "Great visuals",
            "Gorgeous cinematography with a tight runtime.",
            false,
            31,
            created.plusDays(20),
            updated
        )
    );
    return new WorkResponse(
        workId,
        "Neon Skyline",
        MediaType.MOVIE,
        2022,
        created,
        updated,
        reviews
    );
  }
}
