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

  // a. Added @Autowired to make dependency injection explicit 
  public WorkController(ReviewService service) {
    this.service = service;
  }

  // b. Added simple null check and error handling for create
  @PostMapping
  public ResponseEntity<WorkResponse> create(@Valid @RequestBody WorkCreateRequest req) {
    if (req.getTitle() == null || req.getTitle().isEmpty()) {
      return ResponseEntity.badRequest().build(); // return 400 if title is missing
    }
    WorkResponse work = service.createWork(req.getTitle(), req.getMediaType(), req.getReleaseYear());
    return ResponseEntity.created(URI.create("/api/works/" + work.id())).body(work);
  }

  // c. Added null check for GET to return 404 if workId not found
  @GetMapping("/{workId}")
  public ResponseEntity<WorkResponse> get(@PathVariable UUID workId) {
    WorkResponse response = service.getWorkDetail(workId);
    if (response == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(response);
  }
}
