package com.page2screen.config;

import com.page2screen.domain.MediaType;
import com.page2screen.domain.Review;
import com.page2screen.domain.Work;
import com.page2screen.repo.ReviewRepository;
import com.page2screen.repo.WorkRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DataSeedService {
  private final WorkRepository workRepository;
  private final ReviewRepository reviewRepository;

  public DataSeedService(WorkRepository workRepository, ReviewRepository reviewRepository) {
    this.workRepository = workRepository;
    this.reviewRepository = reviewRepository;
  }

  @Transactional
  public void clearAll() {
    reviewRepository.deleteAll();
    workRepository.deleteAll();
  }

  @Transactional
  public void seedIfEmpty() {
    if (workRepository.count() > 0) return;
    seedAll();
  }

  @Transactional
  public void seedAll() {
    OffsetDateTime now = OffsetDateTime.now();

    Work book1 = new Work();
    book1.setId(UUID.randomUUID());
    book1.setTitle("The Pragmatic Programmer");
    book1.setMediaType(MediaType.BOOK);
    book1.setReleaseYear(1999);
    book1.setCreatedAt(now);
    book1.setUpdatedAt(now);

    Work book2 = new Work();
    book2.setId(UUID.randomUUID());
    book2.setTitle("Clean Code");
    book2.setMediaType(MediaType.BOOK);
    book2.setReleaseYear(2008);
    book2.setCreatedAt(now);
    book2.setUpdatedAt(now);

    Work movie1 = new Work();
    movie1.setId(UUID.randomUUID());
    movie1.setTitle("The Matrix");
    movie1.setMediaType(MediaType.MOVIE);
    movie1.setReleaseYear(1999);
    movie1.setCreatedAt(now);
    movie1.setUpdatedAt(now);

    Work movie2 = new Work();
    movie2.setId(UUID.randomUUID());
    movie2.setTitle("Inception");
    movie2.setMediaType(MediaType.MOVIE);
    movie2.setReleaseYear(2010);
    movie2.setCreatedAt(now);
    movie2.setUpdatedAt(now);

    List<Work> savedWorks = workRepository.saveAll(List.of(book1, book2, movie1, movie2));

    Review r1 = new Review();
    r1.setId(UUID.randomUUID());
    r1.setWork(savedWorks.get(0));
    r1.setAuthorId(UUID.randomUUID());
    r1.setAuthorDisplayName("Alice");
    r1.setRating(5);
    r1.setTitle("Essential reading for devs");
    r1.setBody("A timeless book with practical advice. Highly recommended.");
    r1.setContainsSpoilers(false);
    r1.setLikes(3);
    r1.setCreatedAt(now.minusDays(10));
    r1.setUpdatedAt(now.minusDays(10));

    Review r2 = new Review();
    r2.setId(UUID.randomUUID());
    r2.setWork(savedWorks.get(1));
    r2.setAuthorId(UUID.randomUUID());
    r2.setAuthorDisplayName("Bob");
    r2.setRating(4);
    r2.setTitle("Great principles, densely packed");
    r2.setBody("Some chapters are repetitive but overall great guidance for writing maintainable code.");
    r2.setContainsSpoilers(false);
    r2.setLikes(5);
    r2.setCreatedAt(now.minusDays(8));
    r2.setUpdatedAt(now.minusDays(8));

    Review r3 = new Review();
    r3.setId(UUID.randomUUID());
    r3.setWork(savedWorks.get(2));
    r3.setAuthorId(UUID.randomUUID());
    r3.setAuthorDisplayName("Charlie");
    r3.setRating(5);
    r3.setTitle("A mind-bending classic");
    r3.setBody("Still holds up. Stunning visual effects and smart story.");
    r3.setContainsSpoilers(false);
    r3.setLikes(12);
    r3.setCreatedAt(now.minusDays(30));
    r3.setUpdatedAt(now.minusDays(30));

    Review r4 = new Review();
    r4.setId(UUID.randomUUID());
    r4.setWork(savedWorks.get(3));
    r4.setAuthorId(UUID.randomUUID());
    r4.setAuthorDisplayName("Dana");
    r4.setRating(5);
    r4.setTitle("Inception is a modern masterpiece");
    r4.setBody("Complex, entertaining, and rewatchable. Nolan at his best.");
    r4.setContainsSpoilers(false);
    r4.setLikes(9);
    r4.setCreatedAt(now.minusDays(15));
    r4.setUpdatedAt(now.minusDays(15));

    reviewRepository.saveAll(List.of(r1, r2, r3, r4));
  }
}
