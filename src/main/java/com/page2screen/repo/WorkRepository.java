package com.page2screen.repo;

import com.page2screen.domain.MediaType;
import com.page2screen.domain.Work;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface WorkRepository extends JpaRepository<Work, UUID> {
  @EntityGraph(attributePaths = "reviews")
  Optional<Work> findWithReviewsById(UUID id);

  @EntityGraph(attributePaths = "reviews")
  List<Work> findByMediaType(MediaType mediaType);
}
