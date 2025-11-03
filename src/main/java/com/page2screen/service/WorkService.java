package com.page2screen.service;

import com.page2screen.domain.Work;
import com.page2screen.domain.MediaType;
import com.page2screen.repo.WorkRepository;
import com.page2screen.web.dto.WorkResponse;
import com.page2screen.web.dto.ReviewResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service for managing Work entities (books and movies).
 * CRUD operations and conversion to DTOs.
 */
@Service
public class WorkService {
    private final WorkRepository workRepo;
    public WorkService(WorkRepository workRepo) {
        this.workRepo = workRepo;
    }

    /**
     * Retrieves work by Id
     * @param workId should be the UUID of the work
     * @return the work entity
     * @throws IllegalArgumentException if work not found
     */
    @Transactional(readOnly = true)
    public Work getWork(UUID workId) {
        return workRepo.findById(workId)
            .orElseThrow(() -> new IllegalArgumentException("work not found"));
    }
    /**
     * Retrieves work details including reviews
     * @param workId the UUID of the work
     * @return work response with nested review list
     * @throws IllegalArgumentException if work not found
     */
    @Transactional(readOnly = true)
    public WorkResponse getWorkDetail(UUID workId) {
        Work work = workRepo.findWithReviewsById(workId)
            .orElseThrow(() -> new IllegalArgumentException("work not found"));
        return toWorkResponse(work);
    }
    /**
     * Creates a work
     * @param title the title of the work
     * @param mediaType BOOK or MOVIE
     * @param releaseYear year of release (must be >= 1800) for validation, I'm p sure there weren't any before 1870?
     * @return the response
     */
    @Transactional
    public WorkResponse createWork(String title, MediaType mediaType, Integer releaseYear) {
        Work w = new Work();
        w.setId(UUID.randomUUID());
        w.setTitle(title);
        w.setMediaType(mediaType);
        w.setReleaseYear(releaseYear);
        w.setCreatedAt(OffsetDateTime.now());
        w.setUpdatedAt(w.getCreatedAt());
        Work saved = workRepo.save(w);
        return toWorkResponse(saved);
    }
    /**
     * Converting Work entity to WorkResponse DTO.
     */
    private WorkResponse toWorkResponse(Work work) {
        List<ReviewResponse> reviews = work.getReviews().stream()
            .map(ReviewMapper::toReviewResponse)
            .toList();
        return new WorkResponse(
            work.getId(),
            work.getTitle(),
            work.getMediaType(),
            work.getReleaseYear(),
            work.getCreatedAt(),
            work.getUpdatedAt(),
            reviews
        );
    }
}
