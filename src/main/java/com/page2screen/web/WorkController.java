package com.page2screen.web;

import com.page2screen.domain.MediaType;
import com.page2screen.service.WorkService;
import com.page2screen.web.dto.WorkCreateRequest;
import com.page2screen.web.dto.WorkResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/works")
@CrossOrigin
public class WorkController {
  private final WorkService works;

  public WorkController(WorkService works) {
    this.works = works;
  }

  @GetMapping
  public List<WorkResponse> list(@RequestParam(value = "mediaType", required = false) MediaType mediaType) {
    return works.listWorks(mediaType);
  }

  @PostMapping
  public ResponseEntity<WorkResponse> create(@Valid @RequestBody WorkCreateRequest req) {
    WorkResponse work = works.createWork(req);
    return ResponseEntity.created(URI.create("/api/works/" + work.id())).body(work);
  }

  @GetMapping("/{workId}")
  public WorkResponse get(@PathVariable UUID workId) {
    return works.getWorkDetail(workId);
  }

  @PutMapping("/{workId}")
  public WorkResponse update(@PathVariable UUID workId, @Valid @RequestBody WorkCreateRequest req) {
    return works.updateWork(workId, req);
  }

  @DeleteMapping("/{workId}")
  public ResponseEntity<Void> delete(@PathVariable UUID workId) {
    works.deleteWork(workId, null);
    return ResponseEntity.noContent().build();
  }
}
