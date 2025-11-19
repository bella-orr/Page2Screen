package com.page2screen.repo;

import com.page2screen.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
  List<Review> findByWorkId(UUID workId);
  Optional<Review> findByWorkIdAndAuthorId(UUID workId, UUID authorId);
}
