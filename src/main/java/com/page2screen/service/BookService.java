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
public class BookService {
  private static final UUID SAMPLE_BOOK_ID    = UUID.fromString("11111111-1111-1111-1111-111111111111");
  private static final UUID SECOND_SAMPLE_ID  = UUID.fromString("22222222-2222-2222-2222-222222222222");
  private static final UUID SAMPLE_REVIEW_ID  = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
  private static final UUID SAMPLE_AUTHOR_ID  = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");

  private final WorkRepository workRepo;

  public BookService(WorkRepository workRepo) {
    this.workRepo = workRepo;
  }

  @Transactional(readOnly = true)
  public WorkResponse getBook(UUID workId) {
    UUID resolvedId = workId != null ? workId : SAMPLE_BOOK_ID;
    return buildSampleBook(resolvedId);
  }

  @Transactional(readOnly = true)
  public List<WorkResponse> listBooks() {
    return List.of(
        buildSampleBook(SAMPLE_BOOK_ID),
        new WorkResponse(
            SECOND_SAMPLE_ID,
            "The Silent Library",
            MediaType.BOOK,
            2018,
            OffsetDateTime.now().minusYears(6),
            OffsetDateTime.now().minusYears(5).plusMonths(2),
            List.of()
        )
    );
  }

  @Transactional
  public WorkResponse createBook(String title, Integer releaseYear) {
    OffsetDateTime now = OffsetDateTime.now();
    return new WorkResponse(
        UUID.randomUUID(),
        title != null && !title.isBlank() ? title : "Untitled Book",
        MediaType.BOOK,
        releaseYear != null ? releaseYear : now.getYear(),
        now,
        now,
        List.of()
    );
  }

  @Transactional
  public WorkResponse updateBook(UUID workId, String title, Integer releaseYear) {
    OffsetDateTime now = OffsetDateTime.now();
    UUID resolvedId = workId != null ? workId : SAMPLE_BOOK_ID;
    return new WorkResponse(
        resolvedId,
        title != null && !title.isBlank() ? title : "Revised Book Title",
        MediaType.BOOK,
        releaseYear != null ? releaseYear : now.getYear(),
        now.minusYears(1),
        now,
        List.of()
    );
  }

  @Transactional
  public void deleteBook(UUID workId) {
    // Stub: no persistence yet. Once wired, remove sample-only implementation.
  }

  private WorkResponse buildSampleBook(UUID workId) {
    OffsetDateTime created = OffsetDateTime.now().minusYears(2);
    OffsetDateTime updated = created.plusMonths(3);
    List<ReviewResponse> reviews = List.of(
        new ReviewResponse(
            SAMPLE_REVIEW_ID,
            workId,
            new ReviewResponse.AuthorSummary(SAMPLE_AUTHOR_ID, "Avid Reader"),
            9,
            "Loved it",
            "Paced well and the characters shine.",
            false,
            24,
            created.plusDays(10),
            updated
        )
    );
    return new WorkResponse(
        workId,
        "The Infinite Shelf",
        MediaType.BOOK,
        2021,
        created,
        updated,
        reviews
    );
  }
}
