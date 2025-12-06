package com.page2screen.web;

import com.page2screen.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signup")
  public String signup(@RequestParam String username, @RequestParam String password, HttpSession session) {
    // In-memory stub: create user and redirect home. Exceptions are handled globally.
    userService.createUser(username, password);
    // Set session attribute so templates can show current user
    session.setAttribute("username", username);
    return "redirect:/";
  }

  @PostMapping("/login")
  public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
    boolean ok = userService.authenticate(username, password);
    if (!ok) {
      // For now, redirect back to login on failure (could add error messages later)
      return "redirect:/login";
    }
    session.setAttribute("username", username);
    return "redirect:/";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    try {
      session.invalidate();
    } catch (Exception ignored) {}
    return "redirect:/";
  }
}
