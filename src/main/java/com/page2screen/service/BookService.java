package com.page2screen.service;

import com.page2screen.domain.MediaType;
import com.page2screen.web.dto.WorkResponse;
import com.page2screen.web.dto.WorkUpsertRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {
  private final WorkService works;

  public BookService(WorkService works) {
    this.works = works;
  }

  public List<WorkResponse> listBooks() {
    return works.listWorks(MediaType.BOOK);
  }

  public WorkResponse getBook(UUID workId) {
    return works.getWorkDetail(workId, MediaType.BOOK);
  }

  public WorkResponse createBook(WorkUpsertRequest req) {
    return works.createWork(MediaType.BOOK, req);
  }

  public WorkResponse updateBook(UUID workId, WorkUpsertRequest req) {
    return works.updateWork(workId, MediaType.BOOK, req);
  }

  public void deleteBook(UUID workId) {
    works.deleteWork(workId, MediaType.BOOK);
  }
}
