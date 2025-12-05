package com.page2screen.web;

import com.page2screen.service.MovieService;
import com.page2screen.web.dto.WorkResponse;
import com.page2screen.web.dto.WorkUpsertRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin
public class MovieController {
  private final MovieService movies;

  public MovieController(MovieService movies) {
    this.movies = movies;
  }

  @GetMapping
  public List<WorkResponse> list() {
    return movies.listMovies();
  }

  @GetMapping("/{movieId}")
  public WorkResponse get(@PathVariable UUID movieId) {
    return movies.getMovie(movieId);
  }

  @PostMapping
  public ResponseEntity<WorkResponse> create(@Valid @RequestBody WorkUpsertRequest req) {
    WorkResponse work = movies.createMovie(req);
    return ResponseEntity.created(URI.create("/api/movies/" + work.id())).body(work);
  }

  @PutMapping("/{movieId}")
  public WorkResponse update(@PathVariable UUID movieId, @Valid @RequestBody WorkUpsertRequest req) {
    return movies.updateMovie(movieId, req);
  }

  @DeleteMapping("/{movieId}")
  public ResponseEntity<Void> delete(@PathVariable UUID movieId) {
    movies.deleteMovie(movieId);
    return ResponseEntity.noContent().build();
  }
}
