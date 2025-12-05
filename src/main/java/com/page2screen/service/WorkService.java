package com.page2screen.service;

import com.page2screen.domain.MediaType;
import com.page2screen.domain.Work;
import com.page2screen.repo.WorkRepository;
import com.page2screen.web.dto.WorkCreateRequest;
import com.page2screen.web.dto.WorkResponse;
import com.page2screen.web.dto.WorkUpsertRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class WorkService {
  private static final String WORK_NOT_FOUND = "Work not found";
  private static final String TITLE_NULL_OR_EMPTY = "Title cannot be NULL or empty";
  private static final String RELEASE_YEAR_REQUIRED = "Release year is required";

  private final WorkRepository workRepo;
  private final ResponseMapper mapper;

  public WorkService(WorkRepository workRepo, ResponseMapper mapper) {
    this.workRepo = workRepo;
    this.mapper = mapper;
  }

  @Transactional(readOnly = true)
  public List<WorkResponse> listWorks(MediaType mediaType) {
    List<Work> works = mediaType != null
        ? workRepo.findByMediaType(mediaType)
        : workRepo.findAll();
    return works.stream().map(mapper::toWorkResponse).toList();
  }

  @Transactional(readOnly = true)
  public WorkResponse getWorkDetail(UUID workId) {
    return getWorkDetail(workId, null);
  }

  @Transactional(readOnly = true)
  public WorkResponse getWorkDetail(UUID workId, MediaType requiredType) {
    Work work = workRepo.findWithReviewsById(workId)
        .orElseThrow(() -> new IllegalArgumentException(WORK_NOT_FOUND));
    ensureType(work, requiredType);
    return mapper.toWorkResponse(work);
  }

  @Transactional
  public WorkResponse createWork(WorkCreateRequest req) {
    return saveNewWork(req.getMediaType(), req.getTitle(), req.getReleaseYear());
  }

  @Transactional
  public WorkResponse createWork(MediaType mediaType, WorkUpsertRequest req) {
    return saveNewWork(mediaType, req.getTitle(), req.getReleaseYear());
  }

  @Transactional
  public WorkResponse updateWork(UUID workId, WorkCreateRequest req) {
    return updateExisting(workId, req.getMediaType(), req.getTitle(), req.getReleaseYear());
  }

  @Transactional
  public WorkResponse updateWork(UUID workId, MediaType mediaType, WorkUpsertRequest req) {
    return updateExisting(workId, mediaType, req.getTitle(), req.getReleaseYear());
  }

  @Transactional
  public void deleteWork(UUID workId, MediaType expectedType) {
    Work work = workRepo.findById(workId)
        .orElseThrow(() -> new IllegalArgumentException(WORK_NOT_FOUND));
    ensureType(work, expectedType);
    workRepo.delete(work);
  }

  private WorkResponse saveNewWork(MediaType mediaType, String title, Integer releaseYear) {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException(TITLE_NULL_OR_EMPTY);
    }
    if (releaseYear == null) {
      throw new IllegalArgumentException(RELEASE_YEAR_REQUIRED);
    }
    if (mediaType == null) {
      throw new IllegalArgumentException("Media type is required");
    }
    Work w = new Work();
    w.setId(UUID.randomUUID());
    w.setTitle(title);
    w.setMediaType(mediaType);
    w.setReleaseYear(releaseYear);
    w.setCreatedAt(OffsetDateTime.now());
    w.setUpdatedAt(w.getCreatedAt());
    Work saved = workRepo.save(w);
    return mapper.toWorkResponse(saved);
  }

  private WorkResponse updateExisting(UUID workId, MediaType mediaType, String title, Integer releaseYear) {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException(TITLE_NULL_OR_EMPTY);
    }
    if (releaseYear == null) {
      throw new IllegalArgumentException(RELEASE_YEAR_REQUIRED);
    }
    Work work = workRepo.findById(workId)
        .orElseThrow(() -> new IllegalArgumentException(WORK_NOT_FOUND));
    ensureType(work, mediaType);
    work.setTitle(title);
    work.setReleaseYear(releaseYear);
    if (mediaType != null) {
      work.setMediaType(mediaType);
    }
    work.setUpdatedAt(OffsetDateTime.now());
    Work saved = workRepo.save(work);
    return mapper.toWorkResponse(saved);
  }

  private void ensureType(Work work, MediaType requiredType) {
    if (requiredType != null && work.getMediaType() != requiredType) {
      throw new IllegalArgumentException(WORK_NOT_FOUND);
    }
  }
}
