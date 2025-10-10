package com.page2screen.web;

import com.page2screen.service.ReviewService;
import com.page2screen.web.dto.WorkCreateRequest;
import com.page2screen.web.dto.WorkResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/works")
public class WorkController {
  private final ReviewService service;

  public WorkController(ReviewService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<WorkResponse> create(@Valid @RequestBody WorkCreateRequest req) {
    WorkResponse work = service.createWork(req.getTitle(), req.getMediaType(), req.getReleaseYear());
    return ResponseEntity.created(URI.create("/api/works/" + work.id())).body(work);
  }

  @GetMapping("/{workId}")
  public WorkResponse get(@PathVariable UUID workId) {
    return service.getWorkDetail(workId);
  }
}
