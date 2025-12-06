package com.page2screen.web;

import com.page2screen.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

    @PostMapping("/signup")
    public String signup(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes ra) {
      try {
        userService.createUser(username, password);
        session.setAttribute("username", username);
        return "redirect:/";
      } catch (IllegalStateException | IllegalArgumentException e) {
        ra.addFlashAttribute("signupError", e.getMessage());
        ra.addFlashAttribute("signupUsername", username);
        ra.addFlashAttribute("showSignup", true);
        return "redirect:/login";
      }
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes ra) {
      boolean ok = userService.authenticate(username, password);
      if (!ok) {
        ra.addFlashAttribute("loginError", "Invalid username or password");
        ra.addFlashAttribute("loginUsername", username);
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
