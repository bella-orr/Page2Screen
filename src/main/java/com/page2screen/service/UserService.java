package com.page2screen.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple in-memory user service stub. Not production-ready — passwords are stored in-memory.
 */
@Service
public class UserService {
  private final Map<String, String> users = new ConcurrentHashMap<>();

  public void createUser(String username, String password) {
    if (username == null || username.isBlank()) throw new IllegalArgumentException("Username is required");
    if (password == null || password.length() < 8) throw new IllegalArgumentException("Password must be at least 8 characters");
    if (users.putIfAbsent(username, password) != null) {
      throw new IllegalStateException("User already exists");
    }
  }

  public boolean authenticate(String username, String password) {
    if (username == null || password == null) return false;
    String existing = users.get(username);
    return existing != null && existing.equals(password);
  }
}
