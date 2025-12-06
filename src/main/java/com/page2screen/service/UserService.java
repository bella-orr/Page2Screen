package com.page2screen.service;

import com.page2screen.domain.User;
import com.page2screen.repo.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(String username, String password) {
    if (username == null || username.isBlank()) throw new IllegalArgumentException("Username is required");
    if (password == null || password.length() < 8) throw new IllegalArgumentException("Password must be at least 8 characters");
    if (userRepository.findByUsername(username).isPresent()) throw new IllegalStateException("User already exists");
    User u = new User();
    u.setId(UUID.randomUUID());
    u.setUsername(username);
    u.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
    u.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
    return userRepository.save(u);
  }

  public boolean authenticate(String username, String password) {
    Optional<User> uOpt = userRepository.findByUsername(username);
    if (uOpt.isEmpty()) return false;
    return BCrypt.checkpw(password, uOpt.get().getPasswordHash());
  }

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public void deleteByUsername(String username) {
    userRepository.findByUsername(username).ifPresent(userRepository::delete);
  }
}
