package com.page2screen.repo;

import com.page2screen.domain.Work;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface WorkRepository extends JpaRepository<Work, UUID> {
  @EntityGraph(attributePaths = "reviews")
  Optional<Work> findWithReviewsById(UUID id);
}
