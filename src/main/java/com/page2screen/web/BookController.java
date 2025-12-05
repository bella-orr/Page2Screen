package com.page2screen.web;

import com.page2screen.service.BookService;
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
@RequestMapping("/api/books")
@CrossOrigin
public class BookController {
  private final BookService books;

  public BookController(BookService books) {
    this.books = books;
  }

  @GetMapping
  public List<WorkResponse> list() {
    return books.listBooks();
  }

  @GetMapping("/{bookId}")
  public WorkResponse get(@PathVariable UUID bookId) {
    return books.getBook(bookId);
  }

  @PostMapping
  public ResponseEntity<WorkResponse> create(@Valid @RequestBody WorkUpsertRequest req) {
    WorkResponse work = books.createBook(req);
    return ResponseEntity.created(URI.create("/api/books/" + work.id())).body(work);
  }

  @PutMapping("/{bookId}")
  public WorkResponse update(@PathVariable UUID bookId, @Valid @RequestBody WorkUpsertRequest req) {
    return books.updateBook(bookId, req);
  }

  @DeleteMapping("/{bookId}")
  public ResponseEntity<Void> delete(@PathVariable UUID bookId) {
    books.deleteBook(bookId);
    return ResponseEntity.noContent().build();
  }
}
