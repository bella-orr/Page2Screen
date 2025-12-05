package com.page2screen.service;

import com.page2screen.domain.MediaType;
import com.page2screen.web.dto.WorkResponse;
import com.page2screen.web.dto.WorkUpsertRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {
  private final WorkService works;

  public MovieService(WorkService works) {
    this.works = works;
  }

  public List<WorkResponse> listMovies() {
    return works.listWorks(MediaType.MOVIE);
  }

  public WorkResponse getMovie(UUID workId) {
    return works.getWorkDetail(workId, MediaType.MOVIE);
  }

  public WorkResponse createMovie(WorkUpsertRequest req) {
    return works.createWork(MediaType.MOVIE, req);
  }

  public WorkResponse updateMovie(UUID workId, WorkUpsertRequest req) {
    return works.updateWork(workId, MediaType.MOVIE, req);
  }

  public void deleteMovie(UUID workId) {
    works.deleteWork(workId, MediaType.MOVIE);
  }
}
