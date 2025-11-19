package com.page2screen.web;

import com.page2screen.service.WorkService;
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
    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @PostMapping
    public ResponseEntity<WorkResponse> create(@Valid @RequestBody WorkCreateRequest req) {
        WorkResponse work = workService.createWork(req.getTitle(), req.getMediaType(), req.getReleaseYear());
        return ResponseEntity.created(URI.create("/api/works/" + work.id())).body(work);
    }

    @GetMapping("/{workId}")
    public WorkResponse get(@PathVariable UUID workId) {
        return workService.getWorkDetail(workId);
    }
}

